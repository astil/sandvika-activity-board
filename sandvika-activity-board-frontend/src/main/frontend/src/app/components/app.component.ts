import {Component, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {AuthCodeService} from "../service/auth-code.service";
import {ActivatedRoute} from "@angular/router";
import {Subscriber} from "rxjs/Subscriber";
import {Athlete} from "../domain/athlete";

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
    providers: [AppRestService]
})
export class AppComponent implements OnInit {
    private isLoggedIn: boolean;
    private authSubscription: Subscriber<boolean>;
    private athlete: Athlete;

    private redirectUrl : string;

    constructor(private authService: AuthCodeService, private activatedRoute: ActivatedRoute) {
        this.isLoggedIn = authService.isAuthenticated;
        authService.isAuthenticatedChange.subscribe((value) => this.isLoggedIn = value);

        this.athlete = authService.athlete;
        this.authService.athleteChange.subscribe((value) => this.athlete = value);
    }

    ngOnInit() {
        this.redirectUrl = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + window.location.pathname;
        this.activatedRoute.queryParams.subscribe(r => {
            if (r['code']) {
                this.authService.loginAttempt(r['code']);
            }
        });

        this.authService.loginAttempt(null);
    }

    ngOnDestroy() {
        //prevent memory leak when component destroyed
        this.authSubscription.unsubscribe();
    }
}
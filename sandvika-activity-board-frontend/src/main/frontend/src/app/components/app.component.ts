import {Component, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {AuthCodeService} from "../service/auth-code.service";
import {ActivatedRoute} from "@angular/router";
import {Subscriber} from "rxjs/Subscriber";
import {Athlete} from "../domain/athlete";
import {CookieService} from "ngx-cookie-service";

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
    private defaultClub: string;

    constructor(private authService: AuthCodeService, private activatedRoute: ActivatedRoute, private cookie: CookieService) {
        this.isLoggedIn = authService.isAuthenticated;
        authService.isAuthenticatedChange.subscribe((value) => this.isLoggedIn = value);

        this.athlete = authService.athlete;
        this.authService.athleteChange.subscribe((value) => this.athlete = value);
    }

    ngOnInit() {
        if (window.location.port && window.location.port !== '80') {
            this.redirectUrl = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + window.location.pathname;
        } else {
            this.redirectUrl = window.location.protocol + "//" + window.location.hostname + window.location.pathname
        }

        if(this.cookie.check('default-club')) {
            this.defaultClub = this.cookie.get('default-club');
        }

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

    registerClub(club: string) {
        this.cookie.set('default-club', club);
        this.defaultClub = club;
    }

    resetClubEmitter(reset: boolean) {
        if (reset) {
            this.defaultClub = null;
        }
    }
}
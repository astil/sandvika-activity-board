import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppRestService} from '../service/app.rest.service';
import {ActivatedRoute} from '@angular/router';
import {Subscriber} from 'rxjs/Subscriber';
import {Athlete} from '../domain/athlete';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  providers: [AppRestService]
})
export class AppComponent implements OnInit, OnDestroy {
  private authSubscription: Subscriber<boolean>;
  private athlete: Athlete;
  private errorMessage: any;

  private redirectUrl: string;
  private defaultClub: string;

  constructor(private appRestService: AppRestService, private activatedRoute: ActivatedRoute, private cookie: CookieService) {

  }

  ngOnInit() {
    if (window.location.port && window.location.port !== '80') {
      this.redirectUrl = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + window.location.pathname;
    } else {
      this.redirectUrl = window.location.protocol + '//' + window.location.hostname + window.location.pathname
    }

    if (this.cookie.check('default-club')) {
      this.defaultClub = this.cookie.get('default-club');
    }

    this.appRestService.getAthleteById(950307).subscribe(
      athlete => {
        this.athlete = athlete;
      },
      error => {
        (this.errorMessage = <any>error)
      })
  }

  ngOnDestroy() {
    // prevent memory leak when component destroyed
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

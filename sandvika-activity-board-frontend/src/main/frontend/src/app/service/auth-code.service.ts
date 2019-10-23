import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Subject} from 'rxjs/Subject';
import {StravaAthlete} from '../domain/StravaAthlete';
import {Athlete} from '../domain/athlete';
import {CookieService} from 'ngx-cookie-service';
import { AppRestService } from './app.rest.service';

@Injectable()
export class AuthCodeService {
    token: string;
    athlete: Athlete;
    athleteChange: Subject<Athlete> = new Subject<Athlete>();

    isAuthenticated = false;
    isAuthenticatedChange: Subject<boolean> = new Subject<boolean>();

    constructor(private http: HttpClient, private cookie: CookieService, private appRestService: AppRestService) {}

    loginAttempt(code: string): void {
        console.log('Logging in');

        const params = new HttpParams();
        params.set('scope', 'activity:read')
        params.set('state', '');
        params.set('code', code);

        if (this.cookie.check('strava-token')) {
            this.http.get<Athlete>(this.appRestService.restUrl
              + 'athlete/login?token=' + this.cookie.get('strava-token'), {params: params})
                .subscribe(data => {
                        this.doLogin(data);
                    },
                    err => console.log(err));
        } else if (code) {
            this.http.get<Athlete>(this.appRestService.restUrl
              + 'athlete/login/token-registration?code=' + code, {params: params})
                .subscribe(data => {
                        this.doLogin(data);
                    },
                    err => console.log(err)
                );
        }
    }

    private doLogin(data) {
        this.athlete = data;
        this.athleteChange.next(this.athlete);
        this.isAuthenticated = true;
        this.token = this.athlete.token;

        this.isAuthenticatedChange.next(this.isAuthenticated);
        console.log('Access token: ' + this.token);
        this.cookie.set('strava-token', this.token);
    }
}


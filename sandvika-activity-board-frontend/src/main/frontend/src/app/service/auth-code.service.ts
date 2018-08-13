import {Injectable} from '@angular/core';
// import {Router} from '@angular/router';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Subject} from "rxjs/Subject";
import {StravaAthlete} from "../domain/StravaAthlete";
import {Athlete} from "../domain/athlete";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class AuthCodeService {
    private _token: string;
    private _athlete: Athlete;
    athleteChange: Subject<Athlete> = new Subject<Athlete>();

    isAuthenticated: boolean = false;
    isAuthenticatedChange: Subject<boolean> = new Subject<boolean>();
    isAdmin: boolean = false;

    constructor(private http: HttpClient, private cookie: CookieService) {

    }

    loginAttempt(code: string): void {
        console.log("Logging in");

        const params = new HttpParams();
        params.set('state', '');
        params.set('code', code);

        if (this.cookie.check('strava-token')) {
            this.http.get<Athlete>('/athlete/login?token=' + this.cookie.get('strava-token'), {params: params})
                .subscribe(data => {
                        this.doLogin(data);
                    },
                    err => console.log(err));
        } else if (code) {
            this.http.get<Athlete>('/athlete/login/token-registration?code=' + code, {params: params})
                .subscribe(data => {
                        this.doLogin(data);
                    },
                    err => console.log(err)
                );
        }
    }

    private doLogin(data) {
        this._athlete = data;
        console.log(data);
        this.athleteChange.next(this._athlete);
        this.isAuthenticated = true;
        this._token = this._athlete.token;
        if(this.athlete.roles){
          this.athlete.roles.forEach(role => {
            if(role === "ROLE_ADMIN"){
              this.isAdmin = true;
            }
          });
        }

        this.isAuthenticatedChange.next(this.isAuthenticated);
        console.log('Access token: ' + this._token);
        this.cookie.set('strava-token', this._token);
    }


  get token(): string {
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }


  get athlete(): Athlete {
    return this._athlete;
  }

  set athlete(value: Athlete) {
    this._athlete = value;
  }
}

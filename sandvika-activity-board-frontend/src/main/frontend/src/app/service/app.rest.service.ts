import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

import {Observable} from 'rxjs/Observable';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import {Activity} from "../domain/activity";
import {Photo} from "../domain/photo";
import {Statistics} from "../domain/Statistics";
import {TabContent} from "../domain/TabContent";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Athlete} from "../domain/athlete";
import {Club} from "../domain/Club";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class AppRestService {
    private restUrl = 'http://localhost:8080/';  // URL to web api
    //private restUrl = '';  // URL to web api

    constructor(private http: HttpClient, private cookie: CookieService) {
    }

    getLeaderboardPoints(activityType, periodType, pageNumber, year, club: String): Observable<Activity[]> {
        return this.http.get<Activity[]>(this.restUrl + "leaderboard/" + club + "/" + activityType + "/" + periodType + "/" + pageNumber + "/" + year)
            .catch(AppRestService.handleError);
    }

    getLeaderBoardTotalPoints(activityType: String, club: String): Observable<Activity[]> {
        return this.http.get<Activity[]>(this.restUrl + "leaderboard/" + club + "/" + activityType + "/competition/")
            .catch(AppRestService.handleError);
    }

    getMontlyTopActivity(limit, club: String): Observable<Activity[]> {
        return this.http.get<Activity[]>(this.restUrl + "activities/" + club + "/month/top/" + limit + "/points")
            .catch(AppRestService.handleError);
    }

    getAllStats(tab: TabContent, club: String): Observable<Statistics> {
        if (tab.altDecode === 'competition') {
            return this.http.get<Statistics>(this.restUrl + "activities/" + club + "/" + tab.activityType.code + "/stats/" + tab.altDecode)
                .catch(AppRestService.handleError);
        } else {
            return this.http.get<Statistics>(this.restUrl + "activities/" + club + "/" + tab.activityType.code + "/stats/" + tab.altDecode + "/" + tab.pageNumber + "/" + tab.year)
                .catch(AppRestService.handleError);
        }
    }

    getAthleteById(id): Observable<Activity[]> {
        return this.http.get<Activity[]>(this.restUrl + "athlete/" + id + "/activities")
            .catch(AppRestService.handleError);
    }

    getLatestActivities(activityType: String, numberOfActivities, club: String): Observable<Activity[]> {
        return this.http.get<Activity[]>(this.restUrl + "/activities/" + club + "/" + activityType + "/latest/" + numberOfActivities)
            .catch(AppRestService.handleError);
    }

    getTopActivities(tab: TabContent, club: String): Observable<Activity[]> {
        if (tab.altDecode === 'competition') {
            return this.http.get<Activity[]>(this.restUrl + "activities/" + club + "/" + tab.activityType.code + "/top/5/" + tab.altDecode)
                .catch(AppRestService.handleError);
        } else {
            return this.http.get<Activity[]>(this.restUrl + "activities/" + club + "/" + tab.activityType.code + "/top/5/" + tab.altDecode + "/" + tab.pageNumber + "/" + tab.year)
                .catch(AppRestService.handleError);
        }
    }

    get10Photos(activityType: string, club: string): Observable<Photo[]> {
        return this.http.get<Photo[]>(this.restUrl + "activities/" + club + "/" + activityType + "/photos/latest/10")
            .catch(AppRestService.handleError);
    }

    getAllClubsWhereAthleteIsAdmin(athleteId: string): Observable<Club[]> {
      return this.http.get<Club[]>(this.restUrl + "club/athlete/admin/" + athleteId, this.getHeaders())
        .catch(AppRestService.handleError);
    }

    addNewAthleteToClub(clubId: string, athleteId: number) {
      return this.http.put<Athlete>(this.restUrl + "club/" + clubId + "/" + athleteId,null, this.getHeaders())
        .catch(AppRestService.handleError);
    }

    refreshActivities(pages: number) {
      return this.http.get(this.restUrl + "activities/refresh/" + pages, this.getHeaders())
        .catch(AppRestService.handleError);
    }

    getAllAthletes() {
      return this.http.get<Athlete[]>(this.restUrl + "athlete")
        .catch(AppRestService.handleError);
    }

    private getHeaders() {
      return  {
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          "Authorization": "Bearer " + this.cookie.get("strava-token")
        })
      };
    }

    private static handleError(error: Response | any) {
        // In a real world app, we might use a remote logging infrastructure
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Promise.reject(errMsg);
    }
}

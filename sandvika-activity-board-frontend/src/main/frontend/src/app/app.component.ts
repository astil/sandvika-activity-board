import {Component, OnInit} from '@angular/core';
import {AppRestService} from './app.rest.service';
import {Athlete} from './athlete'

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    providers: [AppRestService]
})
export class AppComponent implements OnInit {
    athletes: Object[];
    errorMessage: String;

    ngOnInit(): void {

        this.appRestService.getLeaderBoardMonthPoints()
            .subscribe(
                athlete => this.athletes = athlete,
                error =>  this.errorMessage = <any>error);

    }

    constructor(private appRestService: AppRestService) {
    }
}
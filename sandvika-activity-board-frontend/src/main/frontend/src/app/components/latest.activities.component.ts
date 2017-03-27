import {Component, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Activity} from "../domain/activity";

@Component({
    selector: 'latest-activities-component',
    templateUrl: './latest.activities.component.html',
    providers: [AppRestService],
    styleUrls: ['app.component.css']
})
export class LatestActivities implements OnInit {
    private activities: Activity[];
    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        this.appRestService.getLatestActivities(5).subscribe(
            activities => this.processResult(activities),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result): void {
        this.activities = result
    }
}
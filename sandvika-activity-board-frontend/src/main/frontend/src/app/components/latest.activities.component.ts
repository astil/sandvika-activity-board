import {Component, Input, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Activity} from "../domain/activity";

@Component({
    selector: 'latest-activities-component',
    templateUrl: './latest.activities.component.html',
    providers: [AppRestService],
    styleUrls: ['app.component.css']
})
export class LatestActivities implements OnInit {
    @Input() activities: Activity[];

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {

    }
}
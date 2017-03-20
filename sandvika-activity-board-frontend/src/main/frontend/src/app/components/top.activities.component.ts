import {Component, OnInit, Input} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Activity} from "../domain/activity";

@Component({
    selector: 'top-activities-component',
    templateUrl: './top.activities.component.html',
    providers: [AppRestService],
    styleUrls: ['app.component.css']
})
export class TopActivities implements OnInit {
    @Input('periodtype') periodtype: String;
    @Input('numberofactivities') numberofactivities: number;

    private activities: Activity[];
    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        this.appRestService.getTopActivities(5, 'week').subscribe(
            activities => this.processResult(activities),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result): void {
        this.activities = result
    }
}
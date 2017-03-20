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
    @Input() periodType: String;
    @Input() numberOfActivities: number;
    private title: String;

    private activities: Activity[];
    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        if (this.periodType == "competition") {
            this.title = "Best uttelling";
        } else if (this.periodType == "month") {
            this.title = "Best uttelling denne måneden";
        } else if (this.periodType == "week") {
            this.title = "Best uttelling denne uken";
        }

        this.appRestService.getTopActivities(5, this.periodType).subscribe(
            activities => this.processResult(activities),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result): void {
        this.activities = result
    }
}
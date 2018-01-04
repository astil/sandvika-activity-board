import {Component, OnInit, Input} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Activity} from "../domain/activity";
import {TabContent} from "../domain/TabContent";

@Component({
    selector: 'top-activities-component',
    templateUrl: './top.activities.component.html',
    providers: [AppRestService],
    styleUrls: ['app.component.css']
})
export class TopActivities implements OnInit {
    @Input() tab: TabContent;
    @Input() activities: Activity[];

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
    }
}
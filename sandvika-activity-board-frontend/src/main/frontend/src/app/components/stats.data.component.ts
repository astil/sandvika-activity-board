import {Component, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Statistics} from "../domain/Statistics";

@Component({
    selector: 'stats-data-component',
    templateUrl: './stats.data.component.html',
    providers: [AppRestService],
    inputs: ['athlete'],
    styleUrls: ['app.component.css']
})
export class StatsDataComponent implements OnInit {
    private statistics: Statistics[];
    private thisWeekStats: Statistics;
    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        this.appRestService.getAllStatsWeek().subscribe(
            statistics => this.processResult(statistics),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result): void {
        this.thisWeekStats = result
    }
}
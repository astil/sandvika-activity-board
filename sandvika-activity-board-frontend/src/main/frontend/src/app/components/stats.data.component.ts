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
        this.appRestService.getAllStatsWeek(5).subscribe(
            statistics => this.processResult(statistics),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result) : void {
        if (result && result[0]) {
            this.statistics = result;
            this.thisWeekStats = result[0]
        }
    }
}
import {Component, OnInit, Input} from "@angular/core";
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
    @Input() periodType: String;
    private title: String;

    private statistics: Statistics[];
    private thisWeekStats: Statistics;
    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        if (this.periodType == "competition") {
            this.title = "Totalt fram til nå";
        } else if (this.periodType == "month") {
            this.title = "Totalt denne måned en";
        } else if (this.periodType == "week") {
            this.title = "Totalt denne uken";
        }

        this.appRestService.getAllStats(this.periodType).subscribe(
            statistics => this.processResult(statistics),
            error => this.errorMessage = <any>error
        );
    }

    processResult(result): void {
        this.thisWeekStats = result
    }
}
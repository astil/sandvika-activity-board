import {Component, OnInit, Input} from '@angular/core';
import {AppRestService} from '../service/app.rest.service';
import {Statistics} from '../domain/Statistics';
import {TabContent} from '../domain/TabContent';
import {Observable} from 'rxjs/Observable';
import {observable} from 'rxjs/symbol/observable';

@Component({
    selector: 'stats-data-component',
    templateUrl: './stats.data.component.html',
    providers: [AppRestService],
    inputs: ['athlete'],
    styleUrls: ['app.component.css']
})
export class StatsDataComponent implements OnInit {
    @Input() tab: TabContent;
    @Input() thisWeekStats: Statistics;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
    }
}

import {Component, OnInit} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";
import {Athlete} from "../domain/athlete";
import {NgbTabChangeEvent} from "@ng-bootstrap/ng-bootstrap";
import {TabContent} from "../domain/TabContent";

@Component({
    selector: 'ngbd-tabset-pills',
    templateUrl: './nav.tabset.pills.html',
    styleUrls: ['app.component.css'],
    providers: [AppRestService]
})
export class NgbdTabsetPills implements OnInit {
    private athletes: Athlete[];
    private pillTab: TabContent[] = [new TabContent("all", "Totalt", "competition"), new TabContent("month", "Måned", "month"), new TabContent("week", "Uke", "week")];

    private errorMessage: any;

    constructor(private appRestService: AppRestService) {
    }

    ngOnInit(): void {
        this.appRestService.getLeaderBoardTotalPoints()
            .subscribe(
                athlete => this.athletes = athlete,
                error => this.errorMessage = <any>error);
    }

    public beforeChange($event: NgbTabChangeEvent) {
        switch ($event.nextId) {
            case "all" :
                this.appRestService.getLeaderBoardTotalPoints()
                    .subscribe(
                        athlete => this.athletes = athlete,
                        error => this.errorMessage = <any>error);
                break;
            case "month" :
                this.appRestService.getLeaderBoardMonthPoints()
                    .subscribe(
                        athlete => this.athletes = athlete,
                        error => this.errorMessage = <any>error);
                break;
            case "week" :
                this.appRestService.getLeaderBoardWeekPoints()
                    .subscribe(
                        athlete => this.athletes = athlete,
                        error => this.errorMessage = <any>error);
                break;
        }
    };

}

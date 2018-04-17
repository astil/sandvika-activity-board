import {Component, Input, OnInit} from '@angular/core';
import {AppRestService} from "../service/app.rest.service";
import {Photo} from "../domain/photo";
import {Observable} from "rxjs/Observable";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-photo-carousel',
    providers: [AppRestService],
    templateUrl: './photo-carousel.component.html',
    styleUrls: ['./photo-carousel.component.css']
})
export class PhotoCarouselComponent implements OnInit {
    @Input() club: string;
    @Input() activityType: string;
    private photos: Observable<Photo[]>;

    constructor(config: NgbCarouselConfig, private rest: AppRestService) {
        config.interval = 8000;
    }

    ngOnInit() {
        this.photos = this.rest.get10Photos(this.activityType, this.club);
    }

}

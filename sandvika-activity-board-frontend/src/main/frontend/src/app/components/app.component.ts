import {Component} from "@angular/core";
import {AppRestService} from "../service/app.rest.service";

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
    providers: [AppRestService]
})
export class AppComponent {

    constructor(private appRestService: AppRestService) {
    }
}
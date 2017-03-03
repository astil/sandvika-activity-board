import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './components/app.component';
import {AppRestService} from "./service/app.rest.service";
import {NgbdModalComponent, NgbdModalContent} from "./components/ngModal.component";
import {MeterToKilometerPipe} from "./pipes/meterToKmPipe";
import {TimeConverterPipe, ConvertMinToHhMmSs} from "./pipes/timeConverterPipe";
import {OrderBy} from "./pipes/customOrderBy";
import {NgbdTabsetPills} from "./components/nav.tabset.pills";
import {StatsDataComponent} from "./components/stats.data.component";

@NgModule({
    declarations: [
        AppComponent,
        NgbdModalComponent,
        NgbdModalContent,
        NgbdTabsetPills,
        StatsDataComponent,
        MeterToKilometerPipe,
        TimeConverterPipe,
        OrderBy,
        ConvertMinToHhMmSs
    ],
    entryComponents: [NgbdModalContent],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        NgbModule.forRoot()
    ],
    providers: [AppRestService],
    bootstrap: [AppComponent]
})
export class AppModule {
}

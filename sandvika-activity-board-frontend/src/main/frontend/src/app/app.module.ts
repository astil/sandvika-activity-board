import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {AppRestService} from "./app.rest.service";
import {NgbdModalComponent, NgbdModalContent} from "./ngModal.component";
import {MeterToKilometerPipe} from "./pipe/meterToKmPipe";
import {TimeConverterPipe, ConvertMinToHhMmSs} from "./pipe/timeConverterPipe";
import {OrderBy} from "./pipe/customOrderBy";

@NgModule({
    declarations: [
        AppComponent, NgbdModalComponent, NgbdModalContent, MeterToKilometerPipe, TimeConverterPipe, OrderBy, ConvertMinToHhMmSs
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

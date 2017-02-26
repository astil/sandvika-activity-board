import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {AppRestService} from "./app.rest.service";
import {NgbdModalComponent, NgbdModalContent} from "./ngModal.component";

@NgModule({
    declarations: [
        AppComponent, NgbdModalComponent, NgbdModalContent
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

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSortModule } from '@angular/material';

import { AppComponent } from './components/app.component';
import { AppRestService } from './service/app.rest.service';
import { NgbdModalComponent, NgbdModalContent } from './components/ngModal.component';
import { MeterToKilometerPipe } from './pipes/meterToKmPipe';
import {
  ConvertMinToHhMmSs,
  ConvertMinToHhT,
  ConvertNumberToMonth,
  ConvertNumberToMonthMinimal,
  TimeConverterPipe
} from './pipes/timeConverterPipe';
import { OrderBy } from './pipes/customOrderBy';
import { NgbdTabsetPills } from './components/nav.tabset.pills';
import { StatsDataComponent } from './components/stats.data.component';
import { LatestActivities } from './components/latest.activities.component';
import { TopActivities } from './components/top.activities.component';
import { OAuthModule } from 'angular-oauth2-oidc';
import { HttpClientModule } from '@angular/common/http';
import { AuthCodeService } from './service/auth-code.service';
import { RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { PhotoCarouselComponent } from './photo-carousel/photo-carousel.component';
import { AdminComponent } from './components/admin/admin.component';
import { RefreshActivitiesComponent } from './components/admin/refresh-activities/refresh-activities.component';
import { AddAthleteToClubComponent } from './components/admin/add-athlete-to-club/add-athlete-to-club.component';
import { TitleCasePipe } from '@angular/common';
import { SortService } from './components/sort.service';
import {DeleteActivityComponent} from './components/admin/delete-activity/delete-activity.component';

@NgModule({
  declarations: [
    AppComponent,
    NgbdModalComponent,
    NgbdModalContent,
    NgbdTabsetPills,
    StatsDataComponent,
    LatestActivities,
    TopActivities,
    MeterToKilometerPipe,
    TimeConverterPipe,
    ConvertNumberToMonth,
    ConvertNumberToMonthMinimal,
    OrderBy,
    ConvertMinToHhMmSs,
    ConvertMinToHhT,
    PhotoCarouselComponent,
    AdminComponent,
    RefreshActivitiesComponent,
    AddAthleteToClubComponent,
    DeleteActivityComponent
  ],
  entryComponents: [NgbdModalContent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSortModule,
    FormsModule,
    HttpClientModule,
    OAuthModule.forRoot(),
    NgbModule.forRoot(),
    RouterModule.forRoot([{ path: '', component: AppComponent }])
  ],
  providers: [AppRestService, AuthCodeService, CookieService, SortService],
  bootstrap: [AppComponent]
})
export class AppModule {}

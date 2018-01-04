import {Component, Input, OnInit} from '@angular/core';

import {NgbModal, NgbActiveModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {AppRestService} from "../service/app.rest.service";
import {Activity} from "../domain/activity";
import {Athlete} from "../domain/athlete";

@Component({
    selector: 'ngbd-modal-content',
    template: `
    <div class="modal-header">
      <h4 class="modal-title">{{athlete.athleteFirstName}} {{athlete.athleteLastName}}</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
        <div class="panel panel-default">
            <table class="table table-striped table-condensed">
                <thead>
                    <tr>
                        <th class="d-none d-md-table-cell">Dato</th>
                        <th class="d-md-table-cell">Navn</th>
                        <th class="d-md-table-cell">Type</th>
                        <th class="d-md-table-cell">Poeng</th>
                        <th class="d-none d-md-table-cell">Distanse</th>
                        <th class="d-none d-md-table-cell">Tid i bevegelse</th>
                        <th class="d-none d-md-table-cell">Totaltid</th>
                        <th class="d-none d-md-table-cell">Høydemeter</th>
                        <th class="d-none d-md-table-cell">SufferScore</th>
                        <th class="d-md-table-cell">HC</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor="let activity of activities | orderBy:'-startDateLocal'">
                        <td class="d-none d-md-table-cell">{{activity.startDateLocal | date: 'dd/MM/yyyy'}}</td>
                        <td class="d-md-table-cell"><a href="https://www.strava.com/activities/{{activity.id}}">{{activity.name}}</a></td>
                        <td class="d-md-table-cell">{{activity.type}}</td>
                        <td class="d-md-table-cell">{{activity.points | number : '1.0-1'}}</td>
                        <td class="d-none d-md-table-cell">{{activity.distanceInMeters | meterToKm}} km</td>
                        <td class="d-none d-md-table-cell">{{activity.movingTimeInSeconds | convertToHours}}</td>
                        <td class="d-none d-md-table-cell">{{activity.elapsedTimeInSeconds | convertToHours}}</td>
                        <td class="d-none d-md-table-cell">{{activity.totalElevationGaininMeters}}</td>
                        <td class="d-none d-md-table-cell">{{activity.sufferScore}}</td>  
                        <td class="d-md-table-cell">{{activity.handicap | number : '1.0-1'}}</td>  
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" (click)="activeModal.close('Close click')">Close</button>
    </div>
  `
})
export class NgbdModalContent {
    @Input() activities;
    @Input() athlete;

    constructor(public activeModal: NgbActiveModal) {}
}

@Component({
    selector: 'ngbd-modal-component',
    template: '<a href="#" (click)="open()">{{athlete.athleteFirstName}} {{athlete.athleteLastName}}</a>',
    providers: [AppRestService],
    inputs: ['athlete'],
    styleUrls: ['app.component.css']
})
export class NgbdModalComponent implements OnInit {
    private activities: Activity[];
    private errorMessage: any;
    private athlete: Athlete;

    constructor(private modalService: NgbModal, private appRestService: AppRestService) {}

    open() {
        let options: NgbModalOptions = {
            windowClass: "modal-custom-size"
        };
        const modalRef = this.modalService.open(NgbdModalContent, options);
        modalRef.componentInstance.activities = this.activities;
        modalRef.componentInstance.athlete = this.athlete;
    }

    ngOnInit(): void {
        this.appRestService.getAthleteById(this.athlete.athleteId).subscribe(
            activity => this.activities = activity,
            error =>  this.errorMessage = <any>error);
    }
}

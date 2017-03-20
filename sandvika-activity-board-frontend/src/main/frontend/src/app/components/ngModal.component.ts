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
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Dato</th>
                        <th>Navn</th>
                        <th>Type</th>
                        <th>Poeng</th>
                        <th>Distanse</th>
                        <th>Tid i bevegelse</th>
                        <th>Totaltid</th>
                        <th>Høydemeter</th>
                        <th>SufferScore</th>
                        <th>Handicap</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor="let activity of activities | orderBy:'-startDateLocal'">
                        <td>{{activity.startDateLocal | date: 'dd/MM/yyyy'}}</td>
                        <td><a href="https://www.strava.com/activities/{{activity.id}}">{{activity.name}}</a></td>
                        <td>{{activity.type}}</td>
                        <td>{{activity.points}}</td>
                        <td>{{activity.distanceInMeters | meterToKm}} km</td>
                        <td>{{activity.movingTimeInSeconds | convertToHours}}</td>
                        <td>{{activity.elapsedTimeInSeconds | convertToHours}}</td>
                        <td>{{activity.totalElevationGaininMeters}}</td>
                        <td>{{activity.sufferScore}}</td>  
                        <td>{{activity.handicap}}</td>  
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
    inputs: ['athlete']
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
        this.appRestService.getAthleteByLastName(this.athlete.athleteLastName).subscribe(
            activity => this.activities = activity,
            error =>  this.errorMessage = <any>error);
    }
}
import {Component, Input, OnInit} from '@angular/core';

import {NgbModal, NgbActiveModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {AppRestService} from "./app.rest.service";
import {Activity} from "./activities";
import {Athlete} from "./athlete";

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
            <table class="table">
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
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor="let activity of activitys">
                        <td>{{activity.startDateLocal | date: 'dd/MM/yyyy'}}</td>
                        <td>{{activity.name}}</td>
                        <td>{{activity.type}}</td>
                        <td>{{activity.points}}</td>
                        <td>{{activity.distanceInMeters}}</td>
                        <td>{{activity.movingTimeInSeconds}}</td>
                        <td>{{activity.elapsedTimeInSeconds}}</td>
                        <td>{{activity.totalElevationGaininMeters}}</td>
                        <td>{{activity.sufferScore}}</td>  
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
    @Input() activitys;
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
    private activitys: Activity[];
    private errorMessage: any;
    private athlete: Athlete;

    constructor(private modalService: NgbModal, private appRestService: AppRestService) {}

    open() {
        let options: NgbModalOptions = {
            windowClass: "modal-custom-size"
        };
        const modalRef = this.modalService.open(NgbdModalContent, options);
        modalRef.componentInstance.activitys = this.activitys;
        modalRef.componentInstance.athlete = this.athlete;
    }

    ngOnInit(): void {
        this.appRestService.getAthleteByLastName(this.athlete.athleteLastName).subscribe(
            activity => this.activitys = activity,
            error =>  this.errorMessage = <any>error);
    }
}
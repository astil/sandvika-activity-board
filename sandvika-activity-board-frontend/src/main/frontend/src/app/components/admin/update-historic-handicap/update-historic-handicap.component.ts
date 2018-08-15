import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AppRestService} from '../../../service/app.rest.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-update-historic-handicap',
  templateUrl: './update-historic-handicap.component.html'
})
export class UpdateHistoricHandicapComponent implements OnInit {

  @Output()
  success: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  error: EventEmitter<string> = new EventEmitter<string>();
  isLoadingAthleteHandicap = false;
  isLoadingAllHandicaps = false;
  constructor(private restService: AppRestService) { }

  ngOnInit() {
  }

  updateAthleteHandicap(form: NgForm): void {
    this.isLoadingAthleteHandicap = true;
    this.restService.updateAthleteHistoricHandicap(form.value.athleteId).subscribe(() => {
      this.success.emit('Handikapp har blitt oppdatert for: ' + form.value.athleteId);
      this.isLoadingAthleteHandicap = false;
      form.reset();
    }, (error) => {
      this.error.emit(error);
      this.isLoadingAthleteHandicap = false;
    })
  }

  updateAllHandicaps(form: NgForm): void {
    this.isLoadingAllHandicaps = true;
    this.restService.updateAllHistoricHandicaps(form.value.days).subscribe(() => {
      this.success.emit('Handikapp for all atleter har blitt oppdatert');
      this.isLoadingAllHandicaps = false;
      form.reset();
    }, (error) => {
      this.error.emit(error);
      this.isLoadingAllHandicaps = false;
    })
  }

}

import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AppRestService} from '../../../service/app.rest.service';
import {NgForm} from '@angular/forms';
import {Club} from '../../../domain/Club';
import {AuthCodeService} from '../../../service/auth-code.service';

@Component({
  selector: 'app-set-new-competition-start-date',
  templateUrl: './set-new-competition-start-date.component.html'
})
export class SetNewCompetitionStartDateComponent implements OnInit {

  @Output()
  success: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  error: EventEmitter<string> = new EventEmitter<string>();
  clubs: Club[];
  constructor(private restService: AppRestService, private auth: AuthCodeService) { }

  ngOnInit() {
    this.restService.getAllClubsWhereAthleteIsAdmin(this.auth.athlete.id).subscribe(data => {
      this.clubs = data;
    });
  }

  setNewCompetitionStartDate(form: NgForm) {
    this.restService.updateCompetitionStartDate(form.value.club, form.value.date + 'T00:00:000Z').subscribe(() => {
      this.success.emit(`Ny start dato for ${form.value.club} er ${form.value.date}`);
      form.reset();
    }, (error) => {
      this.error.emit(error);
    })
  }

}

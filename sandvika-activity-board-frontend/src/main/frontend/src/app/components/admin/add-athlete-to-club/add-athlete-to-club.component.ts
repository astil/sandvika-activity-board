import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import {NgForm} from '@angular/forms';
import {Athlete} from '../../../domain/athlete';
import {AppRestService} from '../../../service/app.rest.service';
import {AuthCodeService} from '../../../service/auth-code.service';
import {Club} from '../../../domain/Club';

@Component({
  selector: 'app-add-athlete-to-club',
  templateUrl: './add-athlete-to-club.component.html'
})
export class AddAthleteToClubComponent implements OnInit {

  clubs: Club[];
  options: string[];
  @Output()
  success: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  error: EventEmitter<string> = new EventEmitter<string>();

  constructor(private restService: AppRestService, private auth: AuthCodeService) { }

  ngOnInit() {

    this.restService.getAllClubsWhereAthleteIsAdmin(this.auth.athlete.id).subscribe(data => {
      this.clubs = data;
    });

    this.restService.getAllAthletes().subscribe((athletes: Athlete[]) => {
      const temp = [];
      athletes.forEach(athlete => {
        temp.push(athlete.id);
      });
      this.options = temp;
    });
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map((text) => this.options.filter((athlete) => athlete.toString().indexOf(text) === 0))
    );

  onAthleteSubmit(form: NgForm) {
    const athleteId = Number(form.value.athleteId);
    const clubId = form.value.club;
    this.restService.addNewAthleteToClub(clubId, athleteId).subscribe(() => {
      this.success.emit(`AtletId ${athleteId} har blitt lagt til i klubb ${clubId}`);
      form.reset()
    }, (error) => {
      this.error.emit(error);
    })
  }

}

import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AppRestService} from '../../../service/app.rest.service';
import {FormArray, FormBuilder, FormControl} from '@angular/forms';
import {Club} from '../../../domain/Club';

@Component({
  selector: 'app-create-new-club',
  templateUrl: './create-new-club.component.html'
})
export class CreateNewClubComponent implements OnInit {

  clubForm = this.fb.group({
    name: [''],
    date: [''],
    admins: this.fb.array([
      this.fb.control('')
    ]),
    members: this.fb.array([
      this.fb.control('')
    ])
  });

  @Output()
  success: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  error: EventEmitter<string> = new EventEmitter<string>();
  constructor(private restService: AppRestService, private fb: FormBuilder) { }

  ngOnInit() {
  }

  createNewClub() {

    const club = new Club();
    club.id = this.id.value;
    club.competitionStartDate = this.date.value;
    club.adminIds = this.admins.value;
    club.memberIds = this.members.value;

    const date = new Date(this.date.value);
    date.setHours(0);
    date.setMinutes(0);
    date.setMilliseconds(0);
    club.competitionStartDate = date;

    this.restService.createNewClub(club).subscribe(() => {
      this.success.emit(`Lagt til klubb ${this.id.value}`);
      this.clubForm.reset();
    }, (error) => {
      this.error.emit(error);
    })
  }

  get id() {
    return this.clubForm.get('name');
  }

  get date() {
    return this.clubForm.get('date');
  }

  addAdmin() {
    this.admins.push(this.fb.control(''));
  }

  get admins() {
    return this.clubForm.get('admins') as FormArray;
  }

  addMember() {
    this.members.push(this.fb.control(''));
  }

  get members() {
    return this.clubForm.get('members') as FormArray;
  }

}

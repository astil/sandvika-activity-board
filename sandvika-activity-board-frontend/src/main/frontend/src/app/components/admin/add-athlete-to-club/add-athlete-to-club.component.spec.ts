import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAthleteToClubComponent } from './add-athlete-to-club.component';

describe('AddAthleteToClubComponent', () => {
  let component: AddAthleteToClubComponent;
  let fixture: ComponentFixture<AddAthleteToClubComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAthleteToClubComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAthleteToClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

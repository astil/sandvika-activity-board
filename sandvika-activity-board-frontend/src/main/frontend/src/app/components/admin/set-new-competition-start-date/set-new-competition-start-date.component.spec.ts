import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetNewCompetitionStartDateComponent } from './set-new-competition-start-date.component';

describe('SetNewCompetitionStartDateComponent', () => {
  let component: SetNewCompetitionStartDateComponent;
  let fixture: ComponentFixture<SetNewCompetitionStartDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetNewCompetitionStartDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetNewCompetitionStartDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

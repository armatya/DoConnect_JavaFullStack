import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAnswerComponent } from './get-answer.component';

describe('GetAnswerComponent', () => {
  let component: GetAnswerComponent;
  let fixture: ComponentFixture<GetAnswerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetAnswerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

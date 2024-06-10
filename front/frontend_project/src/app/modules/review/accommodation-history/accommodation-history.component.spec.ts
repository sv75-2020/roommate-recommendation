import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationHistoryComponent } from './accommodation-history.component';

describe('AccommodationHistoryComponent', () => {
  let component: AccommodationHistoryComponent;
  let fixture: ComponentFixture<AccommodationHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccommodationHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccommodationHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

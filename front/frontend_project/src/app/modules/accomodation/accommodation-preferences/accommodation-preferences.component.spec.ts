import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationPreferencesComponent } from './accommodation-preferences.component';

describe('AccommodationPreferencesComponent', () => {
  let component: AccommodationPreferencesComponent;
  let fixture: ComponentFixture<AccommodationPreferencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccommodationPreferencesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccommodationPreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

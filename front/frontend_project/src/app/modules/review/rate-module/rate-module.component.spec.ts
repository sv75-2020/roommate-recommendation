import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateModuleComponent } from './rate-module.component';

describe('RateModuleComponent', () => {
  let component: RateModuleComponent;
  let fixture: ComponentFixture<RateModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RateModuleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RateModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

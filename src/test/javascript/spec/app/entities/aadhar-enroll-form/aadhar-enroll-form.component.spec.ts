/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { AadharEnrollFormComponent } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.component';
import { AadharEnrollFormService } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.service';
import { AadharEnrollForm } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.model';

describe('Component Tests', () => {

    describe('AadharEnrollForm Management Component', () => {
        let comp: AadharEnrollFormComponent;
        let fixture: ComponentFixture<AadharEnrollFormComponent>;
        let service: AadharEnrollFormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AadharEnrollFormComponent],
                providers: [
                    AadharEnrollFormService
                ]
            })
            .overrideTemplate(AadharEnrollFormComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AadharEnrollFormComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AadharEnrollFormService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AadharEnrollForm(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.aadharEnrollForms[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

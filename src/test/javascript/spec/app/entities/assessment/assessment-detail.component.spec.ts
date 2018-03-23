/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentDetailComponent } from '../../../../../../main/webapp/app/entities/assessment/assessment-detail.component';
import { AssessmentService } from '../../../../../../main/webapp/app/entities/assessment/assessment.service';
import { Assessment } from '../../../../../../main/webapp/app/entities/assessment/assessment.model';

describe('Component Tests', () => {

    describe('Assessment Management Detail Component', () => {
        let comp: AssessmentDetailComponent;
        let fixture: ComponentFixture<AssessmentDetailComponent>;
        let service: AssessmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentDetailComponent],
                providers: [
                    AssessmentService
                ]
            })
            .overrideTemplate(AssessmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Assessment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.assessment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

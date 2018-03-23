/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentComponent } from '../../../../../../main/webapp/app/entities/assessment/assessment.component';
import { AssessmentService } from '../../../../../../main/webapp/app/entities/assessment/assessment.service';
import { Assessment } from '../../../../../../main/webapp/app/entities/assessment/assessment.model';

describe('Component Tests', () => {

    describe('Assessment Management Component', () => {
        let comp: AssessmentComponent;
        let fixture: ComponentFixture<AssessmentComponent>;
        let service: AssessmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentComponent],
                providers: [
                    AssessmentService
                ]
            })
            .overrideTemplate(AssessmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Assessment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.assessments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AssessmentStats } from './assessment-stats.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AssessmentStats>;

@Injectable()
export class AssessmentStatsService {

    private resourceUrl =  SERVER_API_URL + 'api/assessment-stats';

    constructor(private http: HttpClient) { }

    create(assessmentStats: AssessmentStats): Observable<EntityResponseType> {
        const copy = this.convert(assessmentStats);
        return this.http.post<AssessmentStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(assessmentStats: AssessmentStats): Observable<EntityResponseType> {
        const copy = this.convert(assessmentStats);
        return this.http.put<AssessmentStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AssessmentStats>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AssessmentStats[]>> {
        const options = createRequestOption(req);
        return this.http.get<AssessmentStats[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AssessmentStats[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AssessmentStats = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AssessmentStats[]>): HttpResponse<AssessmentStats[]> {
        const jsonResponse: AssessmentStats[] = res.body;
        const body: AssessmentStats[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AssessmentStats.
     */
    private convertItemFromServer(assessmentStats: AssessmentStats): AssessmentStats {
        const copy: AssessmentStats = Object.assign({}, assessmentStats);
        return copy;
    }

    /**
     * Convert a AssessmentStats to a JSON which can be sent to the server.
     */
    private convert(assessmentStats: AssessmentStats): AssessmentStats {
        const copy: AssessmentStats = Object.assign({}, assessmentStats);
        return copy;
    }
}

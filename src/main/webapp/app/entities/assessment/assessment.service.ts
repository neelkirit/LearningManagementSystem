import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Assessment } from './assessment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Assessment>;

@Injectable()
export class AssessmentService {

    private resourceUrl =  SERVER_API_URL + 'api/assessments';

    constructor(private http: HttpClient) { }

    create(assessment: Assessment): Observable<EntityResponseType> {
        const copy = this.convert(assessment);
        return this.http.post<Assessment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(assessment: Assessment): Observable<EntityResponseType> {
        const copy = this.convert(assessment);
        return this.http.put<Assessment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Assessment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Assessment[]>> {
        const options = createRequestOption(req);
        return this.http.get<Assessment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Assessment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Assessment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Assessment[]>): HttpResponse<Assessment[]> {
        const jsonResponse: Assessment[] = res.body;
        const body: Assessment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Assessment.
     */
    private convertItemFromServer(assessment: Assessment): Assessment {
        const copy: Assessment = Object.assign({}, assessment);
        return copy;
    }

    /**
     * Convert a Assessment to a JSON which can be sent to the server.
     */
    private convert(assessment: Assessment): Assessment {
        const copy: Assessment = Object.assign({}, assessment);
        return copy;
    }
}

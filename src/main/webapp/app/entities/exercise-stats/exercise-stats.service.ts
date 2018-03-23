import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ExerciseStats } from './exercise-stats.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ExerciseStats>;

@Injectable()
export class ExerciseStatsService {

    private resourceUrl =  SERVER_API_URL + 'api/exercise-stats';

    constructor(private http: HttpClient) { }

    create(exerciseStats: ExerciseStats): Observable<EntityResponseType> {
        const copy = this.convert(exerciseStats);
        return this.http.post<ExerciseStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(exerciseStats: ExerciseStats): Observable<EntityResponseType> {
        const copy = this.convert(exerciseStats);
        return this.http.put<ExerciseStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ExerciseStats>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ExerciseStats[]>> {
        const options = createRequestOption(req);
        return this.http.get<ExerciseStats[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ExerciseStats[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ExerciseStats = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ExerciseStats[]>): HttpResponse<ExerciseStats[]> {
        const jsonResponse: ExerciseStats[] = res.body;
        const body: ExerciseStats[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ExerciseStats.
     */
    private convertItemFromServer(exerciseStats: ExerciseStats): ExerciseStats {
        const copy: ExerciseStats = Object.assign({}, exerciseStats);
        return copy;
    }

    /**
     * Convert a ExerciseStats to a JSON which can be sent to the server.
     */
    private convert(exerciseStats: ExerciseStats): ExerciseStats {
        const copy: ExerciseStats = Object.assign({}, exerciseStats);
        return copy;
    }
}

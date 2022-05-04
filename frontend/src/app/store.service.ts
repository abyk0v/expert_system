import {Injectable} from "@angular/core";

@Injectable()
export class StoreService {
    private isInitLoading: boolean  = true;

    public getIsInitLoading() : boolean {
        if (this.isInitLoading) {
            this.isInitLoading = false;
        }
        return this.isInitLoading;
    }
}
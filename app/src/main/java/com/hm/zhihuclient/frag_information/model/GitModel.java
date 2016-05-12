package com.hm.zhihuclient.frag_information.model;

import java.io.Serializable;
import java.util.List;

/**
 * project：ZhiHuClient
 * author： FLY
 * date：   2016/4/25
 * time：   15:17
 * purpose：
 */
public class GitModel implements Serializable {

    private boolean error;
    private List<DetailsModel> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DetailsModel> getResults() {
        return results;
    }

    public void setResults(List<DetailsModel> results) {
        this.results = results;
    }

}

package com.nw.primefinder.service;

import com.nw.primefinder.model.GetPrimeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrimeFinderService {
    public GetPrimeResponse getPrimeReaponse(int initial){
        GetPrimeResponse getPrimeResponse = new GetPrimeResponse();
        getPrimeResponse.setInitial(initial);
        getPrimeResponse.setPrimes(getPrimes( initial));
        return getPrimeResponse;
    }

    private List<Integer> getPrimes(int initial){
        List<Integer> list = new ArrayList<>();
        for(int i =2; i<= initial;i++){
            if(isPrime(i)){
                list.add(i);
            }
        }
        return list;
    }

    private boolean isPrime(int n){
        boolean flag = true;
        for(int i = 2; i<= n/2; i++){
            if(n%i ==0){
                flag = false;
                break;
            }
        }
        return flag;
    }
}

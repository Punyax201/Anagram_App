/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

import android.util.Log;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordset=new HashSet<String>();
    private ArrayList<String> wordList=new ArrayList<String>();
    private HashMap<String,ArrayList> lettersToWord=new HashMap<String,ArrayList>();
    private ArrayList<String> anagrams;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);

            wordList.add(word);

            String sorted;
            char[] target=word.toCharArray();
            Arrays.sort(target);
            sorted=String.valueOf(target);

            if(lettersToWord.containsKey(sorted)){
                anagrams=lettersToWord.get(sorted);

                anagrams.add(word);

                lettersToWord.put(sorted,anagrams);

            }
            else{
                ArrayList<String> anagrams=new ArrayList<String>();

                anagrams.add(word);

                lettersToWord.put(sorted,anagrams);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        boolean sub=false;
        int l=base.length();
        String s;
        for (int i=0;i<word.length()-base.length()+1;i++){
            if(word.substring(i,i+l).equals(base)) {
                s=word.substring(i,i+l);
                sub = true;
                Log.d("Sub",s);
            }
            else {
                Log.d("Not-sub","");
            }
        }

        if(wordset.contains(word) && !sub)
            return true;

        else
            return false;
    }

   /* public List<String> getAnagrams(String targetWord) {

        Log.d("tword:", targetWord);
        ArrayList<String> result = new ArrayList<String>();
        char[] target=targetWord.toCharArray();
        Arrays.sort(target);


        for (String str:wordset){

            char[] st=str.toCharArray();
            Arrays.sort(st);
            if(String.valueOf(st).equals(String.valueOf(target))){
                result.add(str);
            }
        }
        lettersToWord.put(targetWord,result);
        Log.d("HashSet:",String.valueOf(lettersToWord));
        Log.d("List:",result.toString()+" tword:"+targetWord);
        return result;
    }*/

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        String x;
        boolean goodcheck=false;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> anagrams;

        for (char i='a';i<='z';i++) {
            String tword = word.concat(String.valueOf(i));


            char t[]=tword.toCharArray();
            Arrays.sort(t);
            String sorted=String.valueOf(t);

            if(lettersToWord.containsKey(sorted)){
                anagrams=lettersToWord.get(sorted);

            }
            else
            {
                anagrams=new ArrayList<String>();
            }

            for (int X=0;X<anagrams.size();X++){
                String finalWord=anagrams.get(X);


                goodcheck=isGoodWord(finalWord,word);
                Log.d("Good?",""+goodcheck+" finalWord "+finalWord+" word"+word);
                if(goodcheck)
                result.add(finalWord);
            }
        }
        Log.d("Result:",result.toString());
        return result;
    }


    //////////Length///////////
    int len=DEFAULT_WORD_LENGTH;

    public String pickGoodStarterWord() {
        String word="";
;
        List<String> anagrams=new ArrayList<>();
        while(anagrams.size()<MIN_NUM_ANAGRAMS){
            word="";
            anagrams=new ArrayList<String>();

            while(word.length()!=len){
                word=wordList.get(random.nextInt(wordList.size()));

                Log.d("New word",word);
            }
                len=word.length();
                anagrams=getAnagramsWithOneMoreLetter(word);
        }
        len++;
        return word;
    }
}

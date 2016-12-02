package com.kamron.pogoiv.logic;

import android.content.Intent;

import com.kamron.pogoiv.Pokefly;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Johan on 2016-11-04.
 * An object which has the ability to broadcast an intent with a pokemon result.
 */

public class PokemonStatsExporter {

    /**
     * Creates an intent to share the result of the pokemon scan as a string formated json blob.
     */
    public void prepareData(Pokefly pokefly, IVScanResult ivScan,String uniquePokemonID, String pokemonCandyAmount, String pokemonNickName,String pokemonName) {

        try{

            File external = Environment.getExternalStorageDirectory();
            String sdcardPath = external.getPath();
            File file = new File(sdcardPath + "/GOIVexport/pokeinfo.csv");

            //if file doesnt exists, then create it
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();

                FileWriter fileWritter = new FileWriter(file,true);
                BufferedWriter out = new BufferedWriter(fileWritter);
                out.append("PseudoUniqueID,PokemonName,CandyName,CandyAmount,pokemonNicknameOCR,PokemonId,AtkMin,AtkMax,DefMin,DefMax,StamMin,StamMax,MinIV,AvgIV,MaxIV,HP,CP,Level\r\n");
                out.close();
                fileWritter.close ();

            }


            FileWriter fileWritter = new FileWriter(file,true);
            BufferedWriter out = new BufferedWriter(fileWritter);

            PokeInfoCalculator calc = PokeInfoCalculator.getInstance();

            out.append(uniquePokemonID.toString()+",");
            out.append(pokemonName.toString()+",");
            out.append(calc.getEvolutionLine(ivScan.pokemon).get(0).toString()+",");
            out.append(pokemonCandyAmount.toString()+",");
            out.append(pokemonNickName.toString()+",");
            out.append(String.format("%s,", (ivScan.pokemon.number + 1)));
            out.append(String.format("%s,", ivScan.lowAttack));
            out.append(String.format("%s,", ivScan.highAttack));
            out.append(String.format("%s,", ivScan.lowDefense));
            out.append(String.format("%s,", ivScan.highDefense));
            out.append(String.format("%s,", ivScan.lowStamina));
            out.append(String.format("%s,", ivScan.highStamina));
            out.append(String.format("%s,", ivScan.getLowestIVCombination().percentPerfect));
            out.append(String.format("%s,", ivScan.getAveragePercent()));
            out.append(String.format("%s,", ivScan.getHighestIVCombination().percentPerfect));
            out.append(String.format("%s,", ivScan.scannedHP));
            out.append(String.format("%s,", ivScan.scannedCP));
            out.append(String.format("%s,", ivScan.estimatedPokemonLevel));
            out.append("\r\n");
            out.close();
            fileWritter.close ();

            System.out.println("Done");

        }catch(IOException e){
            e.printStackTrace();
        }
        /*
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, jsonPokemon.toString());
        sendIntent.setType("application/pokemon-stats");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pokefly.startActivity(sendIntent);*/
    }



}

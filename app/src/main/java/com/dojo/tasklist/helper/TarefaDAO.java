package com.dojo.tasklist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dojo.tasklist.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements iTarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());
        try{
            escreve.insert(DbHelper.TABELA_TAREFAS,null,cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());
        try{
            String[] args = {tarefa.getId().toString()};
            escreve.update(DbHelper.TABELA_TAREFAS,cv,"id=? ",args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS +" ;";
        Cursor c = le.rawQuery(sql,null);

        while(c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}

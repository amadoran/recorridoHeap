/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.espol.recorridoheap.tda;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author amado
 * @param <T>
 */
public class Heap<T>{
    private T[] datos;
    private int tamano;
    
    private final Comparator<T> comp;
    
    public Heap(Comparator<T> comp){
        this.comp = comp;
        this.datos = (T[]) new Object[10];
    }
    
    public boolean offer(T elem){
        if (elem == null)
            throw new NullPointerException();
        
        if(this.tamano >= this.datos.length){
            this.crecer();
        }
        
        this.ordenarSubida(this.tamano, elem);
        tamano++;
        return true;
    }
    
    public T poll(){
        if(isEmpty()){
            return null;
        }
        
        T resultado = this.datos[0];
        
        this.ordenarBajada();
                
        final boolean menorMitad = this.tamano <= this.datos.length / 2;
        final boolean mayorMinimo = this.datos.length > 10;
        
        if(menorMitad && mayorMinimo){
            this.decrecer();
        }
        
        return resultado;
    }
    
    private void crecer(){
        this.datos = Arrays.copyOf(this.datos, this.datos.length * 2);
    }
    
    private void decrecer(){
        T[] tmp = (T[]) new Object[this.datos.length / 2];
        for(int i = 0; i < tmp.length ; i++){
            tmp[i] = this.datos[i];
        }
        
        this.datos = tmp;
    }
    
    private void ordenarSubida(int indice, T elem){
        T nuevo = elem;

        this.datos[indice] = nuevo;
        
        T padre = this.datos[indicePadre(indice)];
        
        while(indice > 0){
            if (comp.compare(nuevo, padre) > 0){
                this.datos[indicePadre(indice)] = nuevo;
                this.datos[indice] = padre;
                indice = indicePadre(indice);
                padre = this.datos[indicePadre(indice)];
            } else{
                return;
            }
        }
    }
    
    private void ordenarBajada(){
        T ultimo = this.datos[this.tamano - 1];
        this.datos[this.tamano - 1] = null;
        tamano--;
        
        this.datos[0] = ultimo;
        
        if(isEmpty()){
            this.datos[0] = null;
            return;
        }
        
        int indice = 0;
        while(indiceHijoDerecho(indice) < this.tamano || indiceHijoIzquierdo(indice) < this.tamano){
            T hijoDerecho = this.datos[indiceHijoDerecho(indice)];
            T hijoIzquierdo = this.datos[indiceHijoIzquierdo(indice)];
            
            if(hijoDerecho == null){
                hijoDerecho = hijoIzquierdo;
            }
            
            boolean mayorHijoDerecho = comp.compare(ultimo, hijoDerecho) < 0;
            boolean mayorHijoIzquierdo = comp.compare(ultimo, hijoIzquierdo) < 0;
            if (mayorHijoDerecho || mayorHijoIzquierdo){
                if(comp.compare(hijoDerecho, hijoIzquierdo) > 0){
                    this.datos[indiceHijoDerecho(indice)] = ultimo;
                    this.datos[indice] = hijoDerecho;
                } else{
                    this.datos[indiceHijoIzquierdo(indice)] = ultimo;
                    this.datos[indice] = hijoIzquierdo;
                }
            }
            
            if(comp.compare(hijoDerecho, hijoIzquierdo) > 0){
                indice = indiceHijoDerecho(indice);
            } else{
                indice = indiceHijoIzquierdo(indice);
            }
        }
    }
    
    public static <T> List<T> heapSort(List<T> lista){
        int tamano = lista.size();
        
        for(int i = tamano / 2 - 1; i >= 0; i--){
            heapify(lista, tamano, i);
        }
        
        for(int i = tamano - 1; i > 0; i--){
            T temp = lista.get(0);
            
            lista.set(0, lista.get(i));
            lista.set(i, temp);
            
            heapify(lista, i, 0);
        }
        
        return lista;
    }
    
    private static <T> void heapify(List<T> lista, int tamano, int raiz){
        int menor = raiz;
        
        int indiceIzquierdo = (2*raiz) + 1;
        int indiceDerecho = 2*(raiz + 1);
        
        if(indiceIzquierdo < tamano && (((Comparable<T>) lista.get(indiceIzquierdo)).compareTo(lista.get(menor)) < 0)){
            menor = indiceIzquierdo;
        }
        
        if(indiceDerecho < tamano && (((Comparable<T>) lista.get(indiceDerecho)).compareTo(lista.get(menor)) < 0)){
            menor = indiceDerecho;
        }
        
        if(menor != raiz){
            T intercambio = lista.get(raiz);
            lista.set(raiz, lista.get(menor));
            lista.set(menor, intercambio);
            
            heapify(lista, tamano, menor);
        }
    }
    
    public boolean isEmpty(){
        return tamano == 0;
    }
    
    public int tamano(){
        return tamano;
    }
    
    public int indicePadre(int indice){
        return (indice-1)/2;
    }
    
    public int indiceHijoDerecho(int indice){
        return 2*(indice + 1);
    }
    
    public int indiceHijoIzquierdo(int indice){
        return (2*indice) + 1;
    }
}
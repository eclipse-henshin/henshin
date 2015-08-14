package org.eclipse.emf.henshin.sam.paf;


/**
 * Eine <code>IFilter</code> Implementierung ist der Ort in dieser Architektur an dem die eigentlichen
 * Berechnung stattfinden. Jeder <code>IFilter</code> ist ein eigenstaendiger <code>Thread</code>. 
 * Die Eingaben fuer die Berechnung holt sich der Filter aus der <code>input</code> Pipe und speichert 
 * die Ergebnisse in der <code>output</code> Pipe. Gestartet wird der <code>IFilter</code> durch 
 * den <code>FilterDispatcher</code> welcher ihn auch wieder vorzeitig beenden kann. 
 */
public interface IFilter<T,S>
extends IProducer<S>, IConsumer<T>
{
   public void filter();

   public long getThreadTime();
   
   public String getName();

}

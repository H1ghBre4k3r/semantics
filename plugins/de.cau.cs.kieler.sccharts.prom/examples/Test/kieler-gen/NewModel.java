/*****************************************************************************/
/*               G E N E R A T E D      J A V A   C O D E                    */
/*****************************************************************************/
/* KIELER - Kiel Integrated Environment for Layout Eclipse RichClient        */
/*                                                                           */
/* http://www.informatik.uni-kiel.de/rtsys/kieler/                           */
/* Copyright 2014 by                                                         */
/* + Kiel University                                  */
/*   + Department of Computer Science                                        */
/*     + Real-Time and Embedded Systems Group                                */
/*                                                                           */
/* This code is provided under the terms of the Eclipse Public License (EPL).*/
/*****************************************************************************/
class NewModel {
   private boolean _GO;
   private boolean PRE_g1;
   public void reset(){
      _GO = true;
      PRE_g1 = false;
      return;
   }
   public void tick(){
      	 boolean g0;
      	 	 boolean g1;
      	 	 boolean g2;
      	 {
         g0 = _GO;
         g2 =(PRE_g1);
         g1 =(_GO||g2);
      }
      PRE_g1 = g1;
      _GO = false;
      return;
   }
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class insDB{
	
	Connection con=null;
	Statement stm=null;
	String con_url="jdbc:derby:ins_db;create=true";
	
	
	public insDB(){	//to setup a connection
		try {
			con=DriverManager.getConnection(con_url);
			stm=con.createStatement();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	public void createDB(){		//to create the database table
	
		
			try{
			stm.executeUpdate("CREATE TABLE inspool (name VARCHAR(20),details VARCHAR(1500))");
			System.out.println("Table created");
			
			
			 insertIns("MOV","Copy from source to destination;;Syntax:;--------;MOV Rd,Rs;MOV M,Rs;MOV Rd,M;;Description;------------;This instruction copies the contents of the source;register into the destination register;the contents of;the source register are not altered. If one of the operands is a;memory location, its location is specified by the contents of;the HL registers;Example:  MOV B, C   or  MOV B, M;");
			 insertIns("MVI", "Move immediate 8-bit;;Syntax:;--------;MVI Rd,data;MVI M,data;;Description;------------;The 8-bit data is stored in the destination register or;memory. If the operand is a memory location, its location is;specified by the contents of the HL registers.;Example:  MVI B, 57H  or  MVI M, 57H;"); 
			 insertIns("LDA", "Load accumulator;;Syntax:;--------;LDA address(16b);;Description;------------;The contents of a memory location, specified by a;16-bit address in the operand, are copied to the accumulator.;The contents of the source are not altered.;Example:  LDA 2034H;");
			 insertIns("LDAX", "Load accumulator indirect;;Syntax:;--------;LDAX B/D Reg.Pair;;Description;------------;The contents of the designated register pair point to a memory;location  into  the  accumulator. The  contents  of  either  the;register pair or the memory location are not altered.;Example:  LDAX B;");
			 insertIns("LXI", "Load register pair immediate ;;Syntax:;--------;LXI Reg.Pair, data(16b);;Description;------------;The instruction loads 16-bit data in the register pair;designated in the operand.;Example:  LXI H, 2034H  or  LXI H, XYZ;");
			 insertIns("LHLD", "Load H and L registers direct;;Syntax:;--------;LHLD address(16b);;Description;------------;The  instruction  copies the  contents  of the  memory  location;pointed  out by the  16-bit address into register L  and copies;the contents of the next memory location into register H.    The;contents of source memory locations are not altered.;Example:  LHLD 2040H;");
			 insertIns("STA", "Store accumulator direct;;Syntax:;--------;STA address(16b);;Description;------------;The contents of the accumulator are copied into the memory;location specified by the operand.    This is a 3-byte instruction,;the second byte specifies the low-order address and the third;byte specifies the high-order address.;Example:  STA 4350H;");
			 insertIns("STAX","Store accumulator indirect;;Syntax:;--------;STAX Reg.Pair;;Description;------------;The contents of the accumulator are copied into the memory;location  specified  by  the  contents  of  the  operand  (registerpair);The contents of the accumulator are not altered.;Example:  STAX B;");
			 insertIns("SHLD", "Store H and L registers direct ;;Syntax:;--------;SHLD address(16b);;Description;------------;The contents of register L are stored into the memory location;specified by the 16-bit address in the operand and the contents;of  H  register  are  stored  into  the  next  memory  location  by;incrementing the operand.      The contents of registers HL are;not  altered.  This  is  a  3-byte  instruction,  the  second  byte;specifies the low-order address and the third byte specifies the;high-order address.;Example:  SHLD 2470H;");
			 insertIns("XCHG", "Exchange H and L with D and E ;;Syntax:;--------;XCHG;;Description;------------;The contents of register H are exchanged with the contents of;register D, and the contents of register L are exchanged with;the contents of register E;Example:  XCHG;");
			 insertIns("SPHL", "Copy H and L registers to the stack pointer;;Syntax:;--------;SPHL;;Description;------------;The instruction loads the contents of the H and L registers into;the  stack  pointer  register,  the contents   of  the H  register;provide  the  high-order  address  and  the  contents  of  the  L;register provide the low-order address.    The contents of the H;and L registers are not altered.;Example:  SPHL;"); 
			 insertIns("XTHL", "Exchange H and L with top of stack;;Syntax:;--------;XTHL;;Description;------------;The contents of the L register  are exchanged with the  stack;location  pointed   out  by  the contents  of  the  stack  pointer;register.  The contents of the H register  are exchanged with;the next  stack location (SP+1); however, the contents of the;stack pointer register are not altered.;Example:  XTHL;");
			 insertIns("PUSH", "Push register pair onto stack;;Syntax:;--------;PUSH Reg.Pair;;Description;------------;The contents of the register pair designated in the operand are;copied onto the  stack in the  following sequence.      The stack;pointer register is decremented and the contents of the high-;order register (B, D, H, A) are copied into that location.     The;stack pointer register is decremented again and the contents of;the  low-order  register   (C,  E,  L,  flags)  are  copied  to  that;location.;Example:  PUSH B or PUSH A;");
			 insertIns("POP", "Pop off stack to register pair;;Syntax:;--------;POP Reg.Pair;;Description;------------;The contents of the memory location pointed out by the stack;pointer register are copied to the low-order register (C, E, L,;status flags) of the operand.   The stack pointer is incremented;by  1 and the contents of that memory location are copied to;the high-order register (B, D, H, A) of the operand.     The stack;pointer register is again incremented by 1.;Example:  POP H or POP A;");
			 insertIns("OUT", "Output data from accumulator to a port with 8-bit address;;Syntax:;--------;OUT port address(8b);;Description;------------;The contents of the accumulator are copied into the I/O port,specified; by the operand.;Example:  OUT F8H;");
			 insertIns("IN", "Input data to accumulator from a port with 8-bit address;;Syntax:;--------;IN port address(8b);;Description;------------;The contents of the input port  designated in the operand are read and loaded into the accumulator. Example:  IN 8CH");
			
			//arithmetic
			
			 insertIns("ADD", "Add register or memory to accumulator;;Syntax:;--------;ADD R;ADD M;;Description;------------;The contents of the operand (register or memory) are;added  to  the  contents  of  the  accumulator  and  the  result  is;stored  in  the  accumulator.      If the  operand    is a  memory;location,  its  location  is  specified by  the  contents  of the  HL;registers.  All  flags are modified  to  reflect  the result  of the;addition.;Example:  ADD B  or  ADD M;");
			 insertIns("ADC", "Add register to accumulator with carry;;Syntax:;--------;ADC R;ADC M;;Description;------------;The contents of the operand (register or memory) and;the  Carry flag are added to the  contents of the  accumulator;and the result is stored in the accumulator.     If the operand is a;memory location, its location is specified by the contents of;the HL registers.   All flags are modified to reflect the result of;the addition.;Example:  ADC B or ADC M");
			 insertIns("ADI", "Add immediate to accumulator;;Syntax:;--------;ADI data(8b);;Description;------------;The   8-bit  data  (operand)  is  added  to  the   contents  of  the;accumulator and the result is stored in the accumulator.         All;flags are modified to reflect the result of the addition.;Example:  ADI  45H;");
			 insertIns("ACI", "Add immediate to accumulator with carry;;Syntax:;--------;ACI data(8b);;Description;------------;The 8-bit data (operand) and the Carry flag are added to the;contents  of  the  accumulator  and  the  result  is  stored  in  the;accumulator.    All flags are modified to reflect the result of the;addition.;Example:  ACI  45H;");
			 insertIns("DAD", "Add register pair to H and L registers;;Syntax:;--------;DAD Reg.Pair;;Description;------------;The  16-bit contents of the specified register pair are added to;the contents of the HL register and the  sum is        stored in the;HL register.    The contents of the source register pair are not;altered.  If the result is larger than  16 bits, the CY flag is set.;No other flags are affected.;");
			 insertIns("SUB", "Subtract register or memory from accumulator;;Syntax:;--------;SUB R;SUB M;;Description;------------;The contents of the operand (register or memory ) are;subtracted from the contents of the accumulator, and the result;is  stored  in  the  accumulator.   If  the  operand  is  a  memory;location,  its  location  is  specified by  the  contents  of the  HL;registers.  All  flags are modified  to  reflect  the result  of the;subtraction.;Example:  SUB B  or  SUB M;");
			 insertIns("SBB", "Subtract source and borrow from accumulator;;Syntax:;--------;SBB R;SBB M;;Description;------------;The contents of the operand (register or memory ) and;the  Borrow    flag  are  subtracted  from  the    contents  of  the;accumulator and the result is placed in the accumulator. If;the operand is a memory location, its location is specified by;the  contents  of the  HL registers.    All  flags  are modified  to;reflect the result of the subtraction.;Example:  SBB B or SBB M;");
			 insertIns("SUI", "Subtract immediate from accumulator;;Syntax:;--------;SUI data(8b);;Description;------------;The 8-bit data (operand) is subtracted from the contents of the;accumulator and the result is stored in the accumulator. All;flags are modified to reflect the result of the subtraction.;Example:  SUI  45H;");
			 insertIns("SBI", "Subtract immediate from accumulator with borrow;;Syntax:;--------;SBI data(8b);;Description;------------;The  8-bit data (operand) and the Borrow flag are subtracted;from the contents of the accumulator and the result is stored;in the accumulator.    All flags are modified to reflect the result;of the subtracion.;Example:  SBI  45H");
			 insertIns("INR", "Increment register or memory by 1;;Syntax:;--------;INR R;INR M;;Description;------------;The contents of the designated register or memory) are;incremented by  1 and the result is stored in the same place. If;the operand is a memory location, its location is specified by;the contents of the HL registers.;Example:  INR B  or  INR M;");
			 insertIns("INX", "Increment register pair by 1;;Syntax:;--------;INX Reg.Pair;;Description;------------;The contents of the designated register pair  are incremented;by 1 and the result is stored in the same place.;Example:  INX H;");
			 insertIns("DCR", "Decrement register or memory by 1;;Syntax:;--------;DCR R;DCR M;;Description;------------;The contents of the designated register or memory are;the operand is a memory location, its location is specified by;the contents of the HL registers.;Example:  DCR B  or  DCR M;");
			 insertIns("DCX","Decrement register pair by 1 ;;Syntax:;--------;DCX R;;Description;------------;The contents of the designated register pair are decremented;by 1 and the result is stored in the same place.;Example:  DCX H;");
			 insertIns("DAA","Decimal adjust accumulator;;Syntax:;--------;DAA;;Description;------------;The  contents  of the  accumulator  are  changed  from a binary value to two 4-bit binary coded decimal (BCD) digits.This is;the only instruction that uses the auxiliary flag to perform the;binary  to BCD  conversion, and the  conversion procedure  is;described below.     S, Z, AC, P, CY flags are altered to reflect;the results of the operation.;If  the  value  of  the  low-order  4-bits  in  the  accumulator  is;greater than 9 or if AC flag is set, the instruction adds 6 to the;low-order four bits.;;greater than 9 or if the Carry flag is set, the instruction adds 6;to the high-order four bits.;Example:  DAA;");
			
			
			//control flow instructions
			
			 insertIns("JMP","Jump unconditionally;;Syntax:;--------;JMP address(16b);;Description;------------;The program  sequence is transferred to the memory location;Example:  JMP 2034H  or JMP XYZ;");
			 insertIns("JC","Jump on Carry;;Syntax:;--------;JC address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the carry flag(CY=1).;Example:  JC 2034H  or JC XYZ;");
			 insertIns("JNC","Jump on NonCarry;;Syntax:;--------;JNC address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the carry flag(CY=0).;Example:  JNC 2034H  or JNC XYZ;");
			 insertIns("JP","Jump on Positive;;Syntax:;--------;JP address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the sign flag(S=0).;Example:  JP 2034H  or JP XYZ;");
			 insertIns("JM","Jump on Minus;;Syntax:;--------;JM address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the sign flag(S=1).;Example:  JM 2034H  or JM XYZ;");
			 insertIns("JZ","Jump on Zero;;Syntax:;--------;JZ address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the zero flag(Z=1).;Example:  JZ 2034H  or JZ XYZ;");
			 insertIns("JNZ","Jump on Non Zero;;Syntax:;--------;JNZ address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the zero flag(Z=1).;Example:  JNZ 2034H  or JNZ XYZ;");
			 insertIns("JPE","Jump on Parity Even;;Syntax:;--------;JPE address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the parity flag(P=1).;Example:  JPE 2034H  or JPE XYZ;");
			 insertIns("JPO","Jump on Parity Odd;;Syntax:;--------;JPO address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the parity flag(P=1).;Example:  JPO 2034H  or JPO XYZ;");
			
			 insertIns("CALL", "Unconditional subroutine call;;Syntax:;--------;CALL address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand.Before;the  transfer, the  address of the next  instruction  after CALL;(the contents of the program counter) is pushed onto the stack.;Example:  CALL 2034H  or CALL XYZ");
			 insertIns("CC", "Call on Carry;;Syntax:;--------;CC address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the carry flag(CY=1). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CC 2034H  or CC XYZ;");
			 insertIns("CNC", "Call on No Carry;;Syntax:;--------;CNC address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the carry flag(CY=0). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CNC 2034H  or CNC XYZ;");
			 insertIns("CP", "Call on Positive;;Syntax:;--------;CP address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the sign flag(S=0). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CP 2034H  or CP XYZ;");
			 insertIns("CM", "Call on Minus;;Syntax:;--------;CM address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the sign flag(S=1). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CM 2034H  or CM XYZ;");
			 insertIns("CZ", "Call on Zero;;Syntax:;--------;CZ address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the zero flag(Z=1). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CZ 2034H  or CZ XYZ;");
			 insertIns("CNZ", "Call on Non Zero;;Syntax:;--------;CNZ address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the zero flag(Z=0). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CNZ 2034H  or CNZ XYZ;");
			 insertIns("CPE", "Call on Positive Even;;Syntax:;--------;CPE address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the parity flag(P=1). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CPE 2034H  or CPE XYZ;");
			 insertIns("CPO", "Call on Positive Odd;;Syntax:;--------;CPO address(16b);;Description;------------;The program  sequence is transferred to the memory location;specified by the  16-bit address given in the operand based on;the parity flag(P=0). Before the;transfer, the address of the next instruction after the call (the;contents of the program counter) is pushed onto the stack.;Example:  CPO 2034H  or CPO XYZ;");
			
			
			 insertIns("RET", "Return from subroutine unconditionally;;Syntax:;--------;RET;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program.  The two bytes from the top of the stack;are copied into the program  counter, and program  execution;begins at the new address.;Example:  RET;");
			 insertIns("RC", "Return on Carry;;Syntax:;--------;RC;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the CARRY flag(CY=1).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RC;");
			 insertIns("RNC", "Return on No Carry;;Syntax:;--------;RNC;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the CARRY flag(CY=0).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RNC;");
			 insertIns("RP", "Return on Positive;;Syntax:;--------;RP;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the SIGN flag(S=0).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RP;");
			 insertIns("RM", "Return on Minus;;Syntax:;--------;RM;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the SIGN flag(S=1).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RM;");
			 insertIns("RZ", "Return on Zero;;Syntax:;--------;RZ;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the ZERO flag(Z=1).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RZ;");
			 insertIns("RNZ", "Return on Non Zero;;Syntax:;--------;RNZ;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the ZERO flag(Z=0).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RNZ;");
			 insertIns("RPE", "Return on Parity Odd;;Syntax:;--------;RPE;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the PARITY flag(P=1).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RPE;");
			 insertIns("RPO", "Return on Parity Even;;Syntax:;--------;RPO;;Description;------------;The program  sequence is transferred  from the  subroutine to;the calling program based on the PARITY flag(P=0).  The two bytes from the top of the stack are;copied  into  the  program counter,  and  program   execution;begins at the new address.;Example:  RPO;");
					
			 insertIns("PCHL","Load program counter with HL contents;;Syntax:;--------;PCHL;;Description;------------;The contents of registers H and L are copied into the program;counter.  The contents of H are placed as the high-order byte;and the contents of L as the low-order byte.;Example:  PCHL;");
			 insertIns("RST", "Restart;;Syntax:;--------;RST 0-7;;Description;------------;The RST instruction is equivalent to a  1-byte call instruction;to one of eight memory locations depending upon the number.;The   instructions  are  generally   used  in  conjunction   with;interrupts  and  inserted  using  external  hardware.   However;these  can be  used  as  software  instructions  in  a program  to;transfer program execution to one of the eight locations.The;addresses are:;;Instruction           Restart Address;;RST 0                  0000H;RST 1                  0008H;RST 2                  0010H;RST 3                  0018H;RST 4                  0020H;RST 5                  0028H;RST 6                  0030H;RST 7                  0038H;;The  8085 has  four additional interrupts and these  interrupts;generate RST instructions internally and thus do not require;any external hardware.     These instructions and their Restart;addresses are:;;Interrupt           Restart Address;TRAP                   0024H;RST 5.5                002CH;RST 6.5                0034H;RST 7.5                003CH;");
			
			
			 insertIns("CMP", "Compare register or memory with accumulator;;Syntax:;--------;CMP R;CMP M;;Description;------------;The contents of the operand (register or memory) are;compared    with   the  contents    of  the  accumulator.      Both;contents  are  preserved    .  The  result  of  the  comparison  is;shown by setting the flags of the PSW as follows:;if (A) < (reg/mem):  carry flag is set;if (A) = (reg/mem):  zero flag is set;if (A) > (reg/mem):  carry and zero flags are reset;Example:  CMP B        or  CMP M;");
			 insertIns("CPI", "Compare immediate with accumulator ;;Syntax:;--------;CPI data(8b);;Description;------------;The second byte (8-bit data) is compared with the contents of;the   accumulator.       The   values   being   compared     remain;unchanged.     The result of the comparison is shown by setting;the flags of the PSW as follows:;if (A) < data:  carry flag is set;if (A) = data:  zero flag is set;if (A) > data:  carry and zero flags are reset;Example:  CPI 89H;");
			 insertIns("ANA", "Logical AND register or memory with accumulator;;Syntax:;--------;ANA R;ANA M;;Description;------------;The contents of the accumulator are logically ANDed with;the  contents  of  the  operand  (register  or  memory),  and  the;result  is  placed  in  the  accumulator.      If  the operand  is   a;memory  location, its address is  specified by the  contents of;HL registers.    S, Z, P are modified to reflect the result of the;operation.  CY is reset.  AC is set.;Example:  ANA B or ANA M;");
			 insertIns("ANI", "Logical AND immediate with accumulator;;Syntax:;--------;ANI data(8b);;Description;------------;The contents of the accumulator are logically ANDed with the;8-bit  data   (operand)    and   the   result   is placed    in  the;accumulator.    S, Z, P are modified to reflect the result of the;operation.  CY is reset.  AC is set.;Example:  ANI 86H;");
			 insertIns("XRA", "Exclusive OR register or memory with accumulator;;Syntax:;--------;XRA R;XRA M;;Description;------------;The contents of the accumulator are Exclusive ORed with;the  contents  of  the  operand  (register  or  memory),  and  the;result  is  placed in  the accumulator.    If  the operand  is  a;memory  location, its address is  specified by the  contents of;HL registers.  S, Z, P are modified to reflect the result of the;operation.  CY and AC are reset.;Example:  XRA B or XRA M;");
			 insertIns("XRI", "Exclusive OR immediate with accumulator;;Syntax:;--------;XRI data(8b);;Description;------------;The contents of the accumulator are Exclusive ORed with the;8-bit  data  (operand)   and   the  result  is  placed   in  the;accumulator.   S, Z, P are modified to reflect the result of the;operation.  CY and AC are reset.;Example:  XRI 86H;");
			 insertIns("ORA", "Logical OR register or memory with accumulaotr;;Syntax:;--------;ORA R;ORA M;;Description;------------;The contents of the accumulator are logically ORed with;the  contents  of  the  operand  (register  or  memory),  and  the;result  is  placed in  the accumulator.    If  the operand  is  a;memory  location, its address is  specified by the  contents of;HL registers.  S, Z, P are modified to reflect the result of the;operation.  CY and AC are reset.;Example:  ORA B or ORA M;"); 
			 insertIns("ORI", "Logical OR immediate with accumulator;;Syntax:;--------;ORI data(8b);;Description;------------;The contents of the accumulator are logically ORed with the;8-bit  data  (operand)   and   the  result  is  placed   in  the;accumulator.   S, Z, P are modified to reflect the result of the;operation.  CY and AC are reset.;Example:  ORI 86H;");
			 insertIns("RLC", "Rotate accumulator left;;Syntax:;--------;RLC;;Description;------------;Each  binary  bit  of  the  accumulator  is  rotated  left  by  one;position.  Bit D7 is placed in the position of D0 as well as in;the Carry flag. CY is modified according to bit D7.     S, Z, P,;AC are not affected.;Example:  RLC;");
			 insertIns("RRC", "Rotate accumulator right;;Syntax:;--------;RRC;;Description;------------;Each  binary  bit  of  the  accumulator  is  rotated  right  by  one;position.  Bit D0 is placed in the position of D7 as well as in;the Carry flag. CY is modified according to bit D0.     S, Z, P,;AC are not affected.;Example:  RRC;");
			 insertIns("RAL", "Rotate accumulator left through carry;;Syntax:;--------;RAL;;Description;------------;Each  binary  bit  of  the  accumulator  is  rotated  left  by  one;position through the Carry flag.     Bit D7 is placed in the Carry;flag,  and  the  Carry  flag  is  placed  in  the  least  significant;position D0.  CY is modified according to bit D7.       S, Z, P, AC;are not affected.;Example:  RAL;");
			
			 insertIns("RAR", "Rotate accumulator right through carry;;Syntax:;--------;RAR;;Description;------------;Each  binary  bit  of  the  accumulator  is  rotated  right  by  one;position through the Carry flag.     Bit D0 is placed in the Carry;flag,  and  the  Carry  flag  is  placed  in  the  most  significant;position D7.  CY is modified according to bit D0.       S, Z, P, AC;are not affected.;Example:  RAR;");
			 insertIns("CMA","Complement accumulator;;Syntax:;--------;CMA;;Description;------------;The contents of the accumulator are complemented.          No flags;are affected.;Example:  CMA;");
			 insertIns("CMC", "Complement carry;;Syntax:;--------;CMC;;Description;------------;The Carry flag is complemented.  No other flags are affected.;Example:  CMC");
			 insertIns("STC", "Set Carry;;Syntax:;--------;STC;;Description;------------;The Carry flag is set to 1.  No other flags are affected.;Example:  STC;");
			 insertIns("NOP", "No operation;;Syntax:;--------;NOP;;Description;------------;No  operation  is performed.     The  instruction  is  fetched  and;decoded.  However no operation is executed.;Example:  NOP;");
			 insertIns("HLT", "Halt and enter wait state;;Syntax:;--------;HLT;;Description;------------;The CPU finishes executing the current instruction and halts;any further execution.     An  interrupt or reset  is necessary to;exit from the halt state.;Example:  HLT;");
			 insertIns("DI", "Disable interrupts;;Syntax:;--------;DI;;Description;------------;The  interrupt  enable  flip-flop  is reset  and  all the  interrupts;except the TRAP are disabled.  No flags are affected.;Example:  DI;");
			 insertIns("EI", "Enable interrupts;;Syntax:;--------;EI;;Description;------------;The  interrupt  enable  flip-flop  is  set  and  all  interrupts  are;enabled.   No  flags are affected.    After  a system reset  or the;acknowledgement  of  an  interrupt,  the  interrupt  enable  flip-;flop is reset, thus disabling the interrupts.   This instruction is;necessary to reenable the interrupts (except TRAP).;Example:  EI;");
			 insertIns("RIM", "Read interrupt mask;;Syntax:;--------;RIM;;Description;------------;This is a multipurpose  instruction used to read the  status of;interrupts  7.5,  6.5,  5.5  and  read  serial  data  input  bit. The;instruction   loads   eight  bits  in  the  accumulator     with  the;following interpretations.;Example:  RIM;");
			 insertIns("SIM", "Set interrupt mask;;Syntax:;--------;SIM;;Description;------------;This is a multipurpose instruction and used to implement the;8085  interrupts  7.5,  6.5,  5.5,  and  serial  data  output.   The;instruction interprets the accumulator contents as follows.;Example:  SIM;");
			

			

			}
			catch(Exception e){System.out.println(e.toString());}
		}
	
	
	public void insertIns(String nam, String des){		//insert instructions into table
	
			int flag;
			try {
				flag = stm.executeUpdate("insert into inspool values('"+nam+"','"+des+"')");
				if(flag==0) System.out.print("Success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			}
			
	
	
	
	
	public void view(){		//print the instructions
		String sql="select * from inspool";
		ResultSet rst;
		try {
				rst = stm.executeQuery(sql);
			
			while(rst.next()){
				System.out.println("Name:"+rst.getString("name"));

				System.out.println("Description:"+rst.getString("details"));
				} 
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	
	

		

}

Explicare Clase:

CommandFactory:
	Am folosit design pattern urile singleton si factory
	Constructorul este privat
	In CommandType am enumerat tipurile de comezni
	CreateCommand este metoda care primeste parametrii de care au nevoie Comenzile si intoarce comanda dorita

Command:
	Este necesara pt design pattern ul Command
	Toate Comenzile extind Clasa abstracta Command care are un camp pentru currentDirectory si metoda execute() 

	Fiecare Comanda are campuri specifice, ceea ce implica un constructor specific ce este apelat in metoda CreateCommand din CommandFactory

Component:
	Clasa abstracta, necesara pt Design Pattern ul Composite
	Voi considera ca atat directoarele, cat si fisierele sunt un "Component".
	Aici sunt metodele ce urmeaza a fi suprascrise in Clasele Directory si File

Directory:
	Fiecare Directory are un nume, un parinte, un ArrayList de elemente de tip Component si un constructor + setters si getters
	Metoda getPath intoarce un string care reprezinta calea catre Director.
		verifica daca directorul este root, caz in care returneaza "/"
		in cazul in care nu este root se va apela recursiv pentru a identifica calea catre parintele directorului. 
		daca parintele parintelui este root avem grija sa  nu mai afisam "/" intre numele directorului si calea catre parinte
	Metoda add primeste un nou Component pe care il adauga in ArrayList ul de Components, sortand ulterios ArrayList-ul.
	Metoda displayName (int lsr) este metoda ce va fi folosita pt Comoanda ls.
		primeste ca parametru un int lsr care poate fi 0, caz in care nu se va face o afisare recursiva sau 1, caz in care afisarea va fi recursiva
		vom afisa intai calea catre directorul nostru si apoi toate Componentele copii ale sale
		in cazul in care se doreste afisarea recursiva, pentru fiecare director copil se va reapela  metoda displayName.
	Metoda getDir are rol in identificarea ultimului director din path ul primit ca input(fie cale absoluta sau relativa)
Practic, se va parcurge calea de la cale[start] pana la cale [stop]
		in cazul in care avem "." se va returna directorul curent(daca start=stop), sau daca avem ".." - directorul parinte daca acesta 
exista sau null in caz contrar(daca start=stop), iar daca nu se va reapela functia pt parintele directorului
		in cazul in care avem numele unui subdirector il vom cauta printre elementele din ArrayList ul directorului; cat timp start nu 
este egal cu stop se va reapela functia pt start+1 
		in final se va returna directorul cautat sau null in cazul in care acesta nu exista

	Metoda getSubComponent primeste un String si intoarce fisierul sau directorul cu numele dat ca parametru daca acesta exista si null daca nu
	Metoda deleteSubComponent primeste un String si un Component. 
		Se va sterge subdirectorul/fisierul din director care are numele dat ca parametru
		Se pastreaza in from parintele Componentului ce urmeaza a fi sters si in toDelete acesta
		Se verifica ca CurrentDirectory sa nu fie inclus in directorul ce urmeaza a fi sters.
		Functia intoarce 0 daca stergerea s a reusit si 1 daca avem eroare pentru a se afisa
	Metoda copyComponents este metoda ce copiaza recursiv intr un nou director tot continutul directorului nostru
		Se parcurge arrayList ul directorului nostru, se creeaza pt fiecare un nou element ce are acelasi nume, dar parintele - directorul
primit ca parametru. in cazul in care elementul ce urmeaza a fi copiat este un director se va apela din nou functia pt a  se copia si elemnetele
continute de acesta.

File:
	Ca laDirectory, avem un nume, un parinte, constructorul, setters si getters
	Metoda displayName afiseaza calea catre fisier
	Metoda getPath returneaza calea catre fisier

Bash:
	Clasa care creeaza sistemul de fisiere
	avem root-radacina si currentDirectory - directorul curent
	Metoda main
		Se creeaza fisierele de output si erori, se pregateste citirea din fisierul cerut
		Se scrie nr instructiunii in cele 2 fisiere
		Se apeleaza pt fiecare instructiune metoda readInstruction care identifica instructiunea, creeaza comanda si urmeaza sa apeleze execute()

Pt Ls:
	Se creeaza comanda si urmeaza sa se execute
	Verificam daca avem ls recursiv(-R) - lsr =1 sau lsr = 0
	Identrific directorul in care se doreste listarea, folosind metodele din Directory
	In final se apeleaza functia displayName 

Pt pwd:
	Se creeaza comanda, se va apela execute unde vom afisa calea catre directorul curent daca nu este root sau "/" daca este root

Pt Cd:
	Se apeleaza execute, Se identifica directorul in care se doreste intrarea, si se modifica CurrentDirectory

Pt Cp:
	In execute() vom identifica folderul in care se doreste mutarea (whereToCopy), precum si fisierul/directorul care se doreste a fi
 mutat (toCopy). 
	In cazul in care unul dintre ele nu exista vom afisa erori
	Verificam sa nu existe duplicat si daca nu avem nicio eroare vom realizarea copierea cu metodacopyComponent explicata anterior

Pt mv:
	Se aseamana cu cp, singura diferenta este ca dupa ce se realizeaza copierea se va sterge elementul ce trebuia sa fie copiat din locul unde era 
initial. De asemenea, se va verifica daca directorul curent este inclus in folderul ce urmeaza a se muta si se va modifica

Pt rm:
	in execute() vom identifica folderul/fisierul ce se doreste a fi mutat, daca nu exista erori se va apela metoda deleteSubComponent explicata anterior
pentru stergerea Componentului 
	

Pt Mkdir/Touch:
	Se apeleaza metoda getStaticComponent pt a se gasi directorul in care urmeaza sa inseram; in cazul in care nu exista se vor afisa erori
	Se creeaza comanda;
	In execute se va crea un nou director/fisier si se va adauga in ArrayList ul de Components in directorul cerut


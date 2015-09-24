# Tutoriel Git & Github

## Installation
Je présume que vous travaillez sous Windows, si pas vous savez certainement mieux que moi comment installer des paquets sur votre \*NIX.   

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/install_git.jpg)

Pour commencer pas de SourceTree ou autre crapulerie, Git fournit un programme formidable que vous pourrez vous procurer ici :  
[http://git-scm.com/downloads]  
Installez-le tout en gardant les paramètres par défaut.

## Initialisation
Une fois installé, lors d'un clic droit daes votre espace de travail, le menu contextuel doit vous présenter les options suivantes :  

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitbash.png)  


L'option Git Bash vous fera apparaître une console Git positionnée à l'endroit où vous avez lancé ce Bash.  
(Vous pouvez utiliser "cd" pour naviguer dans l'arborescence.)   

Avant toute chose il faut configurer Git avec votre identité, entrez les commandes suivantes : 

    git config --global user.name "John McKek"
	git config --global user.email johnkek@example.com

![alt text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitconfig.PNG)

Positionnez-vous dans un dossier qui va accueillir le dossier du projet, "Documents" par exemple.  

Pour récupèrer le projet, il faut le cloner dans votre espace personnel avec la commande:   

    git clone https://github.com/srozen/tvid_app2_1415

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitclone.PNG)

Vous voilà avec le repo dans votre espace personnel !

## Master et les branches

Un projet versionné est, vu d'une manière simple, composé d'un **tronc** commun généralement
appelé Master qui fait état de la version stable du project, et de **branches** sur lesquelles
sont implémentées les fonctionnalités.   
Lorsqu'une fonctionnalité est terminée et accréditée, cette branche rejoint le tronc commun
pour y apporter cette nouvelle fonctionnalité.   
De cette manière, Master reste toujours fonctionnel et le travail est effectué en parallèle de
manière sécurisée.

Master est donc la branche principale qui recueillera les changements collectifs
et celle sur laquelle vous devrez vous mettre à jour.  

Lancez un git bash dans le dossier __tvid_app2_1415__ .   
Maintenant, votre prompt vous dis normalement que vous êtes sur Master.   

Pour savoir sur quelle branche vous êtes:

    git branch

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitbranch.PNG)

Pourchanger de branche et vous déplacer sur Master (Le tronc commun), faites :   
    
	git checkout master

Ensuite, pour télécharger la toute dernière version de Master :   
    
	git pull

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitupdate.PNG)

## Naviguer sur les branches

Maintenant que vous savez tout cela, il est nécéssaire que vous travailliez sur la bonne branche de la feature en cours de développement !   

Par exemple vous devez vous occuper de l'implémentation du core de l'application, avec l'interface de login et inscription. On pourrait iaginer que le nom de la branche soit __app_core__ .

Une fois la branche identifiée, il faut vous positionner dessus :   

    git checkout nom_de_la_branche

Voilà, vous êtes prêts à travailler !

## Travailler sur votre branche

Maintenant que votre branche est prête, au travail !

Assurez-vous de bien être sur votre branche **avant** de modifier les fichiers !! :

    git branch

Sinon positionnez-vous sur votre branche :   

    git checkout votre_branche

Maintenant vous pouvez modifier les fichiers, rédiger le rapport etc...   

Disons maintenant que vous avez terminé votre travail du jour, il est temps de le **soumettre**.   

Vérifiez le statut de votre branche avec : 

    git status

Parfois, vous devrez peut-être créer un fichier, il faut l'ajouter avec :   
(Vous ne serez peut-être pas confrontés à ce cas, exemple en image dans la section suivante)   

    git add le_fichier

Dans tout les cas, vous aurez à acter vos changements via un **commit** : 

    git commit -am "Message de commit entre "" "
	ex : git commit -am "Reponse Q.6.1.1.1-2"

Ceci fait, un **git status** vous dira que tout est clean et qu'il n'y a plus rien à commit, reste plus qu'à soumettre votre travail.


![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitcommit.png)


## Soumettre votre travail
--------------------------

Maintenant que vous avez commité vos changements, plus qu'à mettre en ligne vos changements pour
que je puisse les intégrer au tronc commun : 

    git push origin votre_branche

![alt_text](https://github.com/srozen/beer_collection/blob/documentation/documents/gituto/images/gitcommitpush.PNG)

Voilà, vous avez les bases pour faire du bon travail avec Git.

## Rappel des commandes
-----------------------
1. `git checkout master` Pour vous positionner sur Master
2. `git pull` Pour intégrer les changements sur Master
3. `git checkout votre_branche` Pour revenir sur votre branche
4. `git merge master` Pour transposer les changements de Master sur votre
branche et donc vous mettre à jour.
5. *Vous effectuez votre travail (**Sur votre branche !**)*...
6. `git status` Une fois que vous avez terminé pour voir où vous en êtes.
7. `git add new_file` Si vous avez créé un nouveau fichier à envoyer
8. `git commit -am "votre_msg_de_commit"` Pour commit tout les fichiers
inclus et modifiés (-a) avec un message (-m).
9. `git push origin votre_branche` Pour envoyer votre travail en ligne.

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

//ͼ����Ϣ�������亯��
 
struct bookinfo			//ͼ����Ϣ 
{
	char name[20];		//���� 
	char isbn[20];		//ISBN 
	float price;		//�۸� 
	int num;			//����
	char publish[20];	//������ 
	char pdate[20]; 	//�������� 
	char bookclass[20];	//ͼ����� 
}; 

//����ͼ������ 
struct node
{
	struct bookinfo data;
	struct node* next;
 } ;
struct node* list=NULL;		//����ͼ������ 
// ������ͷ 
 struct node* createhead()
 {
 	struct node* headnode = (struct node*)malloc(sizeof(struct node));
 	headnode->next=NULL;
 	return headnode;
 }
 //������㣺Ϊ������׼��   
 struct node* createnode(struct bookinfo data)
 {
 	struct node* newnode=(struct node*)malloc(sizeof(struct node));
 	newnode->data=data;
 	newnode->next=NULL;
 	return newnode;
  } 

//���룺��ͷ������
void insertnode(struct node* headnode,struct bookinfo data) 
{
	struct node* newnode=createnode(data);
	newnode->next=headnode->next;
	headnode->next=newnode;
}
//��ָ��λ��ɾ�� 
void deletenode(struct node* headnode,char *bookname)
{
	struct node* posleftnode=headnode;
	struct node* posnode=headnode->next;
	while(posnode !=NULL && strcmp(posnode->data.name,bookname))
	{
		posleftnode=posnode;
		posnode=posleftnode->next;
	}		
	if(posnode==NULL)
		return ;
	else
	{
		printf("ɾ���ɹ���\n");
		posleftnode->next=posnode->next;
		free(posnode);
		posnode=NULL;
	}
}
//����ָ��λ�õĽ��   �������Ʋ��� 
struct node* search(struct node* headnode,char* filename)
{
	struct node* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.name,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//����ISNBN�Ų��� 
struct node* searchbyisbn(struct node* headnode,char* tempisbn)
{
	struct node* posnode=headnode->next;
	while(posnode!=NULL&&strcmp(posnode->data.isbn,tempisbn))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//���ճ��������
void searchbypublish(struct node* headnode,char* publishname)
{
	struct node* posnode=headnode->next;
	int  i=0; 
	while(posnode!=NULL)
	{
		if(strcmp(posnode->data.publish,publishname)==0)
		{
			++i;
			printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",posnode->data.name,posnode->data.isbn,posnode->data.price,posnode->data.num,posnode->data.publish,posnode->data.pdate,posnode->data.bookclass);
		}
		posnode=posnode->next;
	}
	if(i==0)
	{
		printf("δ��ѯ�������Ϣ��\n");
	}
 } 
//��ӡ����
void printflist(struct node* headnode)
{
	struct node* pmove=headnode->next ;
	printf("����\t\tISBN\t\t�۸�\t\t����\t\t������\t\t\t��������\t\t���\n");
	while(pmove!=NULL)
	{
		printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",pmove->data.name,pmove->data.isbn,pmove->data.price,pmove->data.num,pmove->data.publish,pmove->data.pdate,pmove->data.bookclass);
		pmove=pmove->next;
	}
}
//�ļ��桢д����
 void saveinfofile(const char *filename,struct node* headnode)
 {
 	FILE *fp=fopen(filename,"w");
 	struct node* pmove=headnode->next;
 	while(pmove!=NULL)
 	{
 		fprintf(fp,"%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",pmove->data.name,pmove->data.isbn,pmove->data.price,pmove->data.num,pmove->data.publish,pmove->data.pdate,pmove->data.bookclass);
 		pmove=pmove->next;
	 }
	 fclose(fp);
 }
 //�ļ�������
 void readinfofromfile(const char *filename,struct node* headnode) 
 {
 	FILE *fp=fopen(filename,"r");
 	if(fp==NULL)
 	{
 		fp=fopen(filename,"w+");		//��һ�δ� ��������һ���ļ� 
	}
	 struct bookinfo tempdata;
	 while(fscanf(fp,"%s\t\t%s\t\t%f\t\t%d\t\t%s\t\t%s\t\t%s\n",tempdata.name,tempdata.isbn,&tempdata.price,&tempdata.num,tempdata.publish,tempdata.pdate,tempdata.bookclass)!=EOF)
	 {
	 	insertnode(list,tempdata);
	 }
 	fclose(fp);
 }
  //ð������ 
 void sortlist(struct node* headnode)
 {
 	for(struct node* p=headnode->next;p!=NULL;p=p->next)
 	{
 		for(struct node* q=headnode->next;q->next!=NULL;q=q->next)
 		{
 			if(q->data.price>q->next->data.price)
 			{
 				struct bookinfo tempdata=q->data;
 				q->data=q->next->data;
 				q->next->data=tempdata;
			 }
		 }
	 }
	 printflist(headnode);
 }


//�û�������ش��� 
struct userinfo			//�û���Ϣ 
{
	char name[20];		//���� 
	int tel;			//�绰
	char loginname[20];	//�û��� 
	char password[20]; 	//���� 
}; 
struct usernode
{
	struct userinfo data;
	struct usernode* next;
 } ;
struct usernode* ulist=NULL;		//�����û���Ϣ���� 
// ������ͷ 
 struct usernode* ucreatehead()
 {
 	struct usernode* headnode = (struct usernode*)malloc(sizeof(struct usernode));
 	headnode->next=NULL;
 	return headnode;
 }
 //������㣺Ϊ������׼��   
 struct usernode* createnode(struct userinfo data)
 {
 	struct usernode* newnode=(struct usernode*)malloc(sizeof(struct usernode));
 	newnode->data=data;
 	newnode->next=NULL;
 	return newnode;
  } 

//���룺��ͷ������
void uinsertnode(struct usernode* headnode,struct userinfo data) 
{
	struct usernode* newnode=createnode(data);
	newnode->next=headnode->next;
	headnode->next=newnode;
}
//
//�ļ��桢д����
 void usaveinfofile(const char *filename,struct usernode* headnode)
 {
 	FILE *fp=fopen(filename,"w");
 	struct usernode* pmove=headnode->next;
 	while(pmove!=NULL)
 	{
 		fprintf(fp,"%s\t\t%d\t\t%s\t\t%s\n",pmove->data.name,pmove->data.tel,pmove->data.loginname,pmove->data.password);
 		pmove=pmove->next;
	 }
	 fclose(fp);
 }
 //�ļ�������
 void ureadinfofromfile(const char *filename,struct usernode* headnode) 
 {
 	FILE *fp=fopen(filename,"r");
 	if(fp==NULL)
 	{
 		fp=fopen(filename,"w+");		//��һ�δ� ��������һ���ļ� 
	}
	 struct userinfo tempdata;
	 while(fscanf(fp,"%s\t\t%d\t\t%s\t\t%s\n",tempdata.name,&tempdata.tel,tempdata.loginname,tempdata.password)!=EOF)
	 {
	 	uinsertnode(ulist,tempdata);
	 }
 	fclose(fp);
 }

//����ָ��λ�õĽ��   �������Ʋ��� 
struct usernode* usearch(struct usernode* headnode,char* filename)
{
	struct usernode* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.name,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//�û���¼ 
struct usernode* usearchbylogin(struct usernode* headnode,char* filename)
{
	struct usernode* posnode=headnode->next;
	while(posnode!=NULL&& strcmp(posnode->data.loginname,filename))
	{
		posnode=posnode->next;
	}
	return posnode;
}
//��ӡ����
void uprintflist(struct usernode* headnode)
{
	struct usernode* pmove=headnode->next ;
	printf("����\t\t�绰\t\t�û���\t\t����\n");
	while(pmove!=NULL)
	{
		printf("%s\t\t%d\t\t%s\t\t%s\n",pmove->data.name,pmove->data.tel,pmove->data.loginname,pmove->data.password);
		pmove=pmove->next;
	}
}




//����¼ϵͳ 
void mainlogin()
{
	printf("----------����ͼ�����ϵͳ--------------\n");
	printf("\t\t0���˳�ϵͳ\n");
	printf("\t\t1��ѧ����¼\n");
	printf("\t\t2������Ա��¼\n");
	printf("------------------------------------------\n");
	printf("�����룺\n");
}


  
  

//����Աҳ��
void admmenu()
{
	printf("---------------����Աϵͳ-----------------------\n");
	printf("\t\tͼ�����ϵͳ\n");
	printf("\t\t0.�˳�ϵͳ\n");
	printf("\t\t1.�Ǽ��鼮\n");
	printf("\t\t2.����鼮\n");
	printf("\t\t3.�鼮�۸�����\n");
	printf("\t\t4.ɾ���鼮\n");
	printf("\t\t5.�����鼮\n");
	printf("\t\t6.�޸�ͼ����Ϣ\n");
	printf("\t\t7.�鿴�û���Ϣ\n");
	printf("-----------------------------------------\n");
	printf("������0~6��");
 } 
 //����Ա����
 void admope()
 {
 	int admopekey;
 	scanf("%d",&admopekey);
 	struct bookinfo tempbook;
 	struct node* result=NULL;
 	switch(admopekey)
	{
	 case 0:
	 			printf("�˳�ϵͳ\n");
				printf("[�˳��ɹ�]");
				system("pause");
				exit(0);
				break;
	 case 1:
	 			printf("�Ǽ��鼮\n"); 
				printf("�����鼮����Ϣ:\n����\tISBN\t�۸�\t����\t������\t��������\t���\n");
				scanf("%s%s%f%d%s%s%s",tempbook.name,tempbook.isbn,&tempbook.price,&tempbook.num,tempbook.publish,tempbook.pdate,tempbook.bookclass); 
				insertnode(list,tempbook); 
				saveinfofile("library.txt",list);
				break;
	 case 2:
	 			printf("����鼮\n"); 
				printflist(list);
				break;
	 case 3:	
	 			printf("�鼮����\n"); 
				sortlist(list);
				break;
	 case 4:
	 			printf("ɾ���鼮\n"); 
				printf("������ɾ���鼮�����ƣ�"); 
				scanf("%s",tempbook.name);
				deletenode(list,tempbook.name);
				saveinfofile("library.txt",list);
				break;
	 case 5:
	 			printf("�����鼮\n"); 
				printf("��ѡ��Ҫ��ѯ�ķ�ʽ��(1~3)\n");
				printf("\t\t1�������鼮���Ʋ�ѯ��\n");
				printf("\t\t2������ISBN�Ų�ѯ��\n");
	 			printf("\t\t3�����ճ������ѯ��\n");
	 			int tempnum;
	 			scanf("%d",&tempnum);
	 			switch(tempnum)
	 			{
	 				case 1:
					 		printf("������Ҫ�����鼮�����ƣ�");
						   scanf("%s",tempbook.name); 
						   result=search(list,tempbook.name);
						   if(result==NULL)
							{
								printf("δ�ҵ������Ϣ��\n");	
							}
						    else
					 		{
							printf("����\t\tISBN\t\t�۸�\t\t����\t\t������\t\t��������\t\t���\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							}
							break;
					case 2:
							printf("������Ҫ�����鼮��ISBN:");
							scanf("%s",tempbook.isbn);
							result=searchbyisbn(list,tempbook.isbn);
							if(result==NULL)
							{
								printf("δ�ҵ������Ϣ��\n");
							}
						    else
					 		{
							printf("����\t\tISBN\t\t�۸�\t\t����\t\t������\t\t��������\t\t���\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							
							}
							break;
					case 3:
							printf("������Ҫ���ҵĳ����磺");
							scanf("%s",tempbook.publish);
							searchbypublish(list,tempbook.publish);
							break;
				}
				break;
	 case 6:	
	 		printf("������Ҫ�޸��鼮�����ƣ�");
			scanf("%s",tempbook.name); 
			result=search(list,tempbook.name);
			if(result==NULL)
					printf("δ�ҵ�����鼮��");
			else
			{
				deletenode(list,tempbook.name);
				printf("�����������鼮����Ϣ:\n���ƣ�ISBN���۸������������磬��������,���\n");
				scanf("%s%s%f%d%s%s%s",tempbook.name,&tempbook.isbn,&tempbook.price,&tempbook.num,tempbook.publish,tempbook.pdate,tempbook.bookclass); 
				insertnode(list,tempbook); 
				printf("�޸���ɣ�"); 
				saveinfofile("library.txt",list);
				break;
			 } 
			break;
	case 7:
			printf("\t\t1����ʾ�����û���Ϣ��\n");
			printf("\t\t2�������ض��û���\n");
			int a;
			scanf("%d",&a);
			switch(a)
			{
				case 1:
					uprintflist(ulist);
					break;
				case 2:
					printf("�������û�����");
					 char nb[20];
					 scanf("%s",nb);
					 struct usernode* uresult=NULL;
					uresult=usearchbylogin(ulist,nb);
					if(uresult==NULL)
					{
						printf("δ���ҵ����û���\n");
						break;
					}
					else{
						printf("����\t\t�绰\t\t�û���\t\t����\n");
						printf("%s\t\t%d\t\t%s\t\t%s\n",uresult->data.name,uresult->data.tel,uresult->data.loginname,uresult->data.password);
						break;
					}
			 } 
			
			break;
	} 
  } 
//����Ա��¼
void admlogin()
{
	char admn[20]="123456789";
	char admp[20]="987654321";
	char adn[20],adp[20];
	printf("�������û�����\n");
	scanf("%s",adn);
	if(strcmp(adn,admn)!=0)
	{
		printf("�û�������\n");
	}
	else
	{
		printf("���������룺\n");
		scanf("%s",adp);
		if(strcmp(adp,"987654321")==0)
		{
			printf("��¼�ɹ���\n");
			while(1)
			{
				system("cls");
				admmenu();
				admope();
				system("pause");
				
			}
		}
		else
		{
			printf("�������"); 
		}
	}
 }  
 
 
 
 
 //ѧ��ҳ��
void stumenu()
{
	printf("--------------ѧ������ϵͳ--------------\n");
	printf("\t\t0���˳�ϵͳ\n");
	printf("\t\t1�����ͼ��\n");
	printf("\t\t2����ѯͼ��\n");
	printf("\t\t3������ͼ��\n");
	printf("\t\t4���黹ͼ��\n");
	printf("\t\t5��ͼ��۸�����\n");
	printf("----------------------------------------\n");
	printf("�����룺\n");
 }  
 //ѧ����¼�˵� 
void stuloginmenu()
{
	printf("----------ѧ��ϵͳ--------------\n");
	printf("\t\t1����¼\n");
	printf("\t\t2��ע��\n");
	printf("\t\t3���޸�����\n");
	printf("--------------------------------\n");
	printf("�����룺\n");
} 

//����ʱ����� 
void time(int month1,int day1,int month2,int day2)
{
	
	if(month1==month2)
	{
		printf("������%d��\n",day2-day1);
	}
	if(month1<month2)
	{
		printf("������%d��\n",day2+30-day1);
	}
}

  



 
//ѧ������
 void stuope()
 {
 	int stuopekey;
 	scanf("%d",&stuopekey);
 	struct bookinfo tempbook;
 	struct node* result=NULL;
 	switch(stuopekey)
 	{
 		case 0:
 			printf("�˳�ϵͳ\n");
			printf("[�˳��ɹ�]");
			system("pause");
			exit(0);
 			break;
 		case 1:
 			printf("����鼮\n"); 
			printflist(list);
 			break;
 		case 2:
 				printf("�����鼮\n"); 
				printf("��ѡ��Ҫ��ѯ�ķ�ʽ��(1~3)\n");
				printf("\t\t1�������鼮���Ʋ�ѯ��\n");
				printf("\t\t2������ISBN�Ų�ѯ��\n");
	 			printf("\t\t3�����ճ������ѯ��\n");
	 			int tempnum;
	 			scanf("%d",&tempnum);
	 			switch(tempnum)
	 			{
	 				case 1:
					 		printf("������Ҫ�����鼮�����ƣ�");
						   scanf("%s",tempbook.name); 
						   result=search(list,tempbook.name);
						   if(result==NULL)
							{
								printf("δ�ҵ������Ϣ��\n");	
							}
						    else
					 		{
							printf("����\t\tISBN\t\t�۸�\t\t����\t\t������\t\t\t��������\t\t���\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							}
							break;
					case 2:
							printf("������Ҫ�����鼮��ISBN:");
							scanf("%s",tempbook.isbn);
							result=searchbyisbn(list,tempbook.isbn);
							if(result==NULL)
							{
								printf("δ�ҵ������Ϣ��\n");
							}
						    else
					 		{
							printf("����\t\tISBN\t\t�۸�\t\t����\t\t������\t\t\t��������\t\t���\n");
							printf("%s\t\t%s\t\t%.1f\t\t%d\t\t%s\t\t%s\t\t%s\n",result->data.name,result->data.isbn,result->data.price,result->data.num,result->data.publish,result->data.pdate,result->data.bookclass);
							
							}
							break;
					case 3:
							printf("������Ҫ���ҵĳ����磺");
							scanf("%s",tempbook.publish);
							searchbypublish(list,tempbook.publish);
							break;
				}
 			break;
 		case 3:
 				printf("�����鼮\n"); 	//�鼮���ڣ��鼮����-1     �����ڣ�����ʧ�� 
				printf("��������ĵ�������");
				scanf("%s",tempbook.name); 
				result=search(list,tempbook.name);
				if(result==NULL)
				{
					printf("û������鼮��");
				}
				else
				{
					if(result->data.name>0)
					{
						result->data.num--;
						saveinfofile("library.txt",list);
						printf("���ĳɹ�������һ����֮�ڹ黹��"); 
					}
					else
					{
						printf("��ǰ�鼮�޿�棬����ʧ�ܣ�"); 
					}
				}
 			break;
 		case 4:
 				printf("�黹�鼮\n"); //�鼮+1 
				printf("������黹���鼮����");
				scanf("%s",tempbook.name); 
				result=search(list,tempbook.name);
				if(result==NULL)
				{
					printf("���鲻���ڱ�ͼ��ݣ�");
				}
				else
				{	int month1,month2,day1,day2;
					printf("���������ʱ��ʱ�䣺\nĳ�·�\tĳ��\n");
					scanf("%d%d",&month1,&day1);
					printf("�����뵱ǰʱ�䣺\nĳ�·�\tĳ��\n");
					scanf("%d%d",&month2,&day2); 
					result->data.num++;
					saveinfofile("library.txt",list);
					printf("�鼮�黹�ɹ���");
					time(month1,day1,month2,day2); 
				 } 
 				break;
 		case 5:
 			printf("�鼮����\n"); 
			sortlist(list);
 			break;
	 }
  } 
  //ѧ����¼ 
void stulogin()
{
	char n[20],p[20];
	printf("\t\t�������û�����\n");
	scanf("%s",n);
	printf("\t\t���������룺\n");
	scanf("%s",p);
	struct usernode* result=NULL;
	result=usearchbylogin(ulist,n);
	if(result==NULL)
	{
		printf("���û���δע�ᣡ\n");
	}
	else
	{
		if(strcmp(p,result->data.password)==0)
		{
			printf("��¼�ɹ���\n");
				while(1)
		{
			system("cls");
			stumenu();
			stuope();
			system("pause");		//��ͣһ�� ����ֹ���� 
			system("cls");			//���� 
		}
		}
		else
		{
			printf("�������\n");
		 } 
	}
 } 
  //ѧ��ѡ�� 
void stuchose()
{
	int stukey;
	scanf("%d",&stukey);
	switch(stukey)
	{
		case 1:
			printf("����¼��\n");
			while(1)
			{
				system("cls");
				stulogin();
				system("pause");
				system("cls");
			}
			break;
		case 2:
			printf("[ע��]\n");
			struct userinfo tempinfo;
			printf("�������������绰���û��������룺\n");
			scanf("%s%d%s%s",tempinfo.name,&tempinfo.tel,tempinfo.loginname,tempinfo.password);
			uinsertnode(ulist,tempinfo); 
			usaveinfofile("�û���Ϣ.txt",ulist);
			break;
		case 3:
			char n[20],p[20],m[20];
			printf("\t\t�������û�����\n");
			scanf("%s",n);
			printf("\t\t���������룺\n");
			scanf("%s",p);
			struct usernode* result=NULL;
			result=usearchbylogin(ulist,n);
			if(result==NULL)
			{
				printf("���û���δע�ᣡ\n");
				break;
			}
			else{
				if(strcmp(p,result->data.password)==0)
				{
					printf("��������������:\n");
					scanf("%s",result->data.password);
					usaveinfofile("�û���Ϣ.txt",ulist);
					printf("�޸ĳɹ���\n"); 
					break;
				 } 
				 else
				 {
				 	printf("�������");
				 	break;
				 }
				
			}
	}
}





//���ܲ��� 
void keydown()
{
	mainlogin();
	int key;
	scanf("%d",&key);
	switch(key)
	{
		case 0:
			printf("�˳�ϵͳ\n");
			printf("[�˳��ɹ�]");
			system("pause");
			exit(0);
 			break;
		case 1://ѧ����¼ 
			while(1)
			{
				stuloginmenu();
				stuchose();
			}
		case 2://����Ա��¼ 
			 while(1)
			 {
			 	system("cls");
			 	admlogin();
			 }
	 } 
}
//������ 
int main()
{
	list=createhead();
	readinfofromfile("library.txt",list);
	ulist=ucreatehead();
	ureadinfofromfile("�û���Ϣ.txt",ulist);
	keydown();
	return 0;
}
 


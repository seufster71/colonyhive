
checkAndCommit() {
	OUTPUT=$(git status --porcelain)
	if [ -z "$OUTPUT" ]; then
		echo "#### No changes found"
	else
		echo "#### Changes found"
		echo $OUTPUT
		echo "#### Add any new files"
		git add .
		echo "#### Committing files"
		git commit -m "${1}"
		echo "#### Pushing to repo"
		git push
		git log -1
		echo "#### Complete"
	fi
}

commitAll() {
	
	ECHO "#### core"
	cd ~/git/colonyhive/colonyhive/src/main/js/core
	checkAndCommit "${1}"
	
	ECHO "#### coreView"
	cd ~/git/colonyhive/colonyhive/src/main/js/coreView
	checkAndCommit "${1}"
	
	ECHO "#### main"
	cd ~/git/colonyhive/colonyhive
	checkAndCommit "${1}"
	
	ECHO "#### Done committing"
}

all() {
	commitAll "$*"
}


	
if [ $# -eq 0 ]; then
	echo "You must supply a comment"
else 
	str="$*"
	echo Your comment is $str
	all $str
fi	

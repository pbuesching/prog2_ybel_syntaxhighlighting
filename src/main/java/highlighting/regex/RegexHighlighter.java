package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;

import java.util.ArrayList;
import java.util.List;

// Done: Implement a simple regex-based highlighting strategy. Unlike the scanning approach, this
// strategy applies each token independently to the entire input text and collects all resulting
// {@code HighlightRegion}s, even if they overlap. Conflicts are resolved in a separate step.

// Done: Make this class extend {@code SyntaxHighlighter}, implement the abstract method {@code
// collectMatches}, and override {@code resolveConflicts} to handle overlapping regions produced by
// the naive regex-based strategy.
public class RegexHighlighter extends SyntaxHighlighter {

  // Done: For each token, find all matches of its pattern in the input text, convert them into
  // {@code HighlightRegion}s, and combine all of these regions into a single list.
  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    List<HighlightRegion> regions = new ArrayList<>();
      for (Token token : tokens) {
          regions.addAll(token.test(text));
    }
    return regions;
  }

  // Done: Resolve overlapping regions. Assume that {@code regions} has been normalised and sorted.
  // For any overlapping regions, keep the one that appears first in this list (which reflects the
  // token order) and discard all later overlapping regions. Longer regions that start at the same
  // position are preferred because of the sorting in {@code normalize}.
  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> regions) {
      List<HighlightRegion> mutable_Regions = new ArrayList<>(regions);
      List<HighlightRegion> resolvedRegions = new ArrayList<>();

    while (!mutable_Regions.isEmpty()) {
        HighlightRegion currentRegion = mutable_Regions.get(0);
        if(mutable_Regions.size() == 1) {
              /* If there is only one Region (left), put it into the resolved list */
              resolvedRegions.add(currentRegion);
              mutable_Regions.remove(0);
        } else {
            /* List is already sorted through normalize(), so the overlapping regions can just be deleted */
            HighlightRegion nextRegion = mutable_Regions.get(1);
            if ((nextRegion.start() >= currentRegion.start()) && (nextRegion.start() < currentRegion.end())) {
                mutable_Regions.remove(1);
            } else {
                /* If the current region and next region dont Overlap, all conflicts for this region have been handled */
                resolvedRegions.add(currentRegion);
                mutable_Regions.remove(0);
            }
        }
    }

    return resolvedRegions;
    //throw new UnsupportedOperationException("not implemented yet");
  }
}
